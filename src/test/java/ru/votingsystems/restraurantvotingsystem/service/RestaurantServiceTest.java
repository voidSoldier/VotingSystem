package ru.votingsystems.restraurantvotingsystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.repository.RestaurantRepository;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.votingsystems.restraurantvotingsystem.RTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {


    @Autowired
    private RestaurantService service;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private RestaurantRepository repository;


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
        RESTAURANT1.setMenu(newMenu);
        service.update(RESTAURANT1);
        assertIterableEquals(newMenu, RESTAURANT1.getMenu());


    }

//    @Test
//    void increaseRating() throws Exception {
//        int oldRating = RESTAURANT2.getRating();
//        service.changeRating(RESTAURANT2.getId(), true);
//        int newRating = RESTAURANT2.getRating();
//        assertEquals(oldRating, newRating);
//    }
//
//    @Test
//    void decreaseRating() throws Exception {
//        int oldRating = RESTAURANT3.getRating();
//        service.changeRating(RESTAURANT3.getId(), false);
//        assertEquals(oldRating, RESTAURANT3.getRating());
//    }

    @Test
    void changeRating() throws Exception {
        int newRating = 42;
        service.setRating(RESTAURANT1_ID, newRating);
        assertEquals(newRating, service.get(RESTAURANT1_ID).getRating());
    }

    @Test
    void voteForRestaurant() throws Exception {
    }
}