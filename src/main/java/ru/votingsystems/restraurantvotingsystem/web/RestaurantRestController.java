package ru.votingsystems.restraurantvotingsystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.service.RestaurantService;

@Controller
public class RestaurantRestController {

    @Autowired
    private RestaurantService service;

    public void voteForRestaurant(User user, int restaurantId) {
        service.voteForRestaurant(user, restaurantId);
    }
}
