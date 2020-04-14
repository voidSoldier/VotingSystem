package ru.votingsystems.restraurantvotingsystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.service.RestaurantService;
import ru.votingsystems.restraurantvotingsystem.service.UserService;

import java.util.List;

@Controller
public class AdminRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    /*
    * FOR USERS
    */

    public List<User> getAll() {return userService.getAll();}

    public User get(int userId){return userService.get(userId);}

    public User getByEmail(String email){return userService.getByEmail(email);}

    public void delete(int userId){userService.delete(userId);}

    public void update(int userId, User user){userService.update(userId, user);}

    public void create(User user){userService.create(user);}


    /*
     * FOR RESTAURANTS
     */

    public void inputNewRestaurant(Restaurant newRestaurantd){restaurantService.inputNewRestaurant(newRestaurantd);}

    public void inputNewMenu(int restaurantId, Dish... dishes) {restaurantService.setNewMenu(restaurantId, dishes);}

//    public void updateMenuOfTheDay(int restaurantId, Menu menu) {}
}
