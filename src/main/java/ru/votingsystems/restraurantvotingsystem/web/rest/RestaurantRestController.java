package ru.votingsystems.restraurantvotingsystem.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.service.RestaurantService;


@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    static final String REST_URL = "/rest/restaurants";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    @PutMapping("/{restaurantId}")
    public void vote(@RequestBody User user, @PathVariable int restaurantId) {
        log.info("user {} is voting for restaurant with id {}", user.getId(), restaurantId);

        service.voteForRestaurant(user, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void inputNewRestaurant(@RequestBody Restaurant newRestaurant) {
        log.info("inputNewRestaurant {}", newRestaurant);

        service.inputNewRestaurant(newRestaurant);
    }



    public void inputNewMenu(int restaurantId, Dish... dishes) {
        log.info("inputNewMenu {} for restaurant {}", dishes, restaurantId);

        service.setNewMenu(restaurantId, dishes);
    }
}




