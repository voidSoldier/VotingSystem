package ru.votingsystems.restraurantvotingsystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.RestaurantRepository;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.votingsystems.restraurantvotingsystem.RTestData.*;
import static ru.votingsystems.restraurantvotingsystem.UTestData.USER;
import static ru.votingsystems.restraurantvotingsystem.UTestData.USER_ID;

class RestaurantServiceTest extends AbstractServiceTest {


    @Autowired
    private RestaurantService service;
    @Autowired
    private RestaurantRepository repository;
    @Autowired
    private UserService userService;


    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        Restaurant created = service.create(newRestaurant);
        int newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
    }

    @Test
    void get() throws Exception {
        Restaurant restaurant = service.get(RESTAURANT1_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT1);
    }

    @Test
    void getWithMenu() throws Exception {
        Restaurant restaurant = service.getWithMenu(RESTAURANT1_ID);
        DISH_MATCHER.assertMatch(restaurant.getMenu(), RESTAURANT1.getMenu());
    }

    @Test
    void getAll() throws Exception {
        List<Restaurant> result = service.getAll();
        RESTAURANT_MATCHER.assertMatch(RESTAURANTS, result);
    }

    @Test
    void getAllWithMenu() throws Exception {
        List<Restaurant> result = service.getAllWithMenu();
        RESTAURANT_MATCHER_WITH_MENU.assertMatch(RESTAURANTS, result);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT1_ID), updated);
    }

    @Test
    void delete() throws Exception {
        service.delete(RESTAURANT1_ID);
        Assertions.assertNull(repository.findById(RESTAURANT1_ID).orElse(null));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }

    @Test
    void setNewMenu() throws Exception {
        List<Dish> newMenu = RESTAURANT2.getMenu();
        RESTAURANT3.setMenu(newMenu);
        service.update(RESTAURANT3);
        assertIterableEquals(newMenu, RESTAURANT3.getMenu());
    }

    @Test
    void increaseRating() throws Exception {
        int oldRating = RESTAURANT2.getRating();
        repository.incrementRating(RESTAURANT2.getId());
        int newRating = RESTAURANT2.getRating();
        assertEquals(oldRating, newRating);
    }

    @Test
    void decreaseRating() throws Exception {
        int oldRating = RESTAURANT3.getRating();
        repository.decrementRating(RESTAURANT3.getId());
        assertEquals(oldRating, RESTAURANT3.getRating());
    }

    @Test
    void changeVote() throws Exception {
        User user = new User(USER);
        user.setVoted(true);
        user.setVotingTime(LocalDateTime.now().withHour(10));

        List<Integer> oldList = new ArrayList<>(user.getRatedRestaurants());
//
        int old04Rating = service.get(100004).getRating();
        int old02Rating = service.get(100002).getRating();

        service.voteForRestaurant(user, RESTAURANT1_ID);

        int new04Rating = service.get(100004).getRating();
        int new02Rating = service.get(100002).getRating();
//
        List<Integer> newList = new ArrayList<>(userService.getWithRestaurants(USER_ID).getRatedRestaurants());
//
        assertTrue(old04Rating > new04Rating);
        assertTrue(old02Rating < new02Rating);
        assertEquals(oldList, newList);
    }
}