package ru.votingsystems.restraurantvotingsystem.web;

import org.springframework.stereotype.Controller;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Menu;
import ru.votingsystems.restraurantvotingsystem.model.User;

import java.util.List;

@Controller
public class AdminRestController {

    /*
    * FOR USERS
    */

    public List<User> getAll() {return null;}

    public User get(int id){return null;}

    public User getByEmail(String email){return null;}

    public void delete(int id){}

    public void update(int id, User user){}

    public void create(User user){}


    /*
     * FOR RESTAURANTS
     */

    public void inputNewRestaurant(int restaurantId){}

    public void inputNewMenu(int restaurantId, Dish... dishes) {}

    public void updateMenuOfTheDay(int restaurantId, Menu menu) {}
}
