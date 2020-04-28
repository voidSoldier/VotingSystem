package ru.votingsystems.restraurantvotingsystem.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.service.RestaurantService;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;

import java.util.List;


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

        service.create(newRestaurant);
    }


    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void inputNewMenu(@PathVariable int restaurantId, @RequestBody List<Dish> dishes) {
        log.info("inputNewMenu {} for restaurant {}", dishes, restaurantId);

        service.setNewMenu(restaurantId, dishes);
    }


    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return service.get(id);
    }


    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant) {
        service.update(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}




