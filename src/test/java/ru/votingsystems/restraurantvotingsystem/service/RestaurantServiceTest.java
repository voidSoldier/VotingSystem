package ru.votingsystems.restraurantvotingsystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.repository.DataJpaRestaurantRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static ru.votingsystems.restraurantvotingsystem.RTestData.*;

class RestaurantServiceTest {


    @Autowired
    private RestaurantService service;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private DataJpaRestaurantRepository repository;


    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        Restaurant created = service.create(newRestaurant);
        int newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
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
        Assertions.assertNull(repository.get(RESTAURANT1_ID));
    }


    @Test
    void setNewMenu() throws Exception {
        service.setNewMenu(RESTAURANT1_ID, RESTAURANT2.getMenu());
        assertIterableEquals(RESTAURANT2.getMenu(), RESTAURANT1.getMenu());
    }

    @Test
    void increaseRating() throws Exception {
        int oldRating = RESTAURANT3.getRating();
        service.increaseRating(RESTAURANT3.getId());
        assertEquals(++oldRating, RESTAURANT3.getRating());
    }

    @Test
    void decreaseRating() throws Exception {
        int oldRating = RESTAURANT3.getRating();
        service.decreaseRating(RESTAURANT3.getId());
        assertEquals(--oldRating, RESTAURANT3.getRating());
    }

    @Test
    void voteForRestaurant() throws Exception {
    }
}