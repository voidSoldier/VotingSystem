package ru.votingsystems.restraurantvotingsystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.votingsystems.restraurantvotingsystem.RestaurantTestData;
import ru.votingsystems.restraurantvotingsystem.UserTestData;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.model.Vote;
import ru.votingsystems.restraurantvotingsystem.repository.RestaurantRepository;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.votingsystems.restraurantvotingsystem.RestaurantTestData.*;
import static ru.votingsystems.restraurantvotingsystem.UserTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {


    @Autowired
    private RestaurantService service;
    @Autowired
    private RestaurantRepository repository;
    @Autowired
    private UserService userService;


    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        Restaurant created = service.create(newRestaurant);
        int newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
    }

    @Test
    void get() throws Exception {
        Restaurant restaurant = service.get(RESTAURANT1_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, RESTAURANT1);
    }

    @Test
    void getAll() throws Exception {
        List<Restaurant> result = service.getAll();
        RESTAURANT_MATCHER.assertMatch(RESTAURANTS, result);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        service.update(new Restaurant(updated));
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
        User user = UserTestData.getNew();
        service.voteForRestaurant(user, RESTAURANT3.getId());

        List<Vote> oldList = new ArrayList<>(user.getVotes());
        int oldR3Rating = service.get(RESTAURANT3.getId()).getRating();
        int oldR1Rating = service.get(RESTAURANT1_ID).getRating();

        user.setVotingTime(LocalDateTime.now().withHour(10));
        service.voteForRestaurant(user, RESTAURANT1_ID);

        int newR3Rating = service.get(RESTAURANT3.getId()).getRating();
        int newR1Rating = service.get(RESTAURANT1_ID).getRating();
        List<Vote> newList = new ArrayList<>(userService.getActivity(USER_ID).getVotes());

        assertTrue(oldR3Rating > newR3Rating);
        assertTrue(oldR1Rating < newR1Rating);
        assertNotEquals(oldList, newList);
    }
}