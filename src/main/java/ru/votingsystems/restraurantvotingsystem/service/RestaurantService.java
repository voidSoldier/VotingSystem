package ru.votingsystems.restraurantvotingsystem.service;

import org.springframework.stereotype.Service;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Menu;

@Service
public class RestaurantService {

    public void inputNewRestaurant(int restaurantId){}

    public void inputNewMenu(int restaurantId, Dish... dishes) {}

    public void updateMenuOfTheDay(int restaurantId, Menu menu) {}

    public void increaseRating(int restaurantId) {}
}
