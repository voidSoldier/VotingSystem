package ru.votingsystems.restraurantvotingsystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;

import java.awt.*;
import java.util.List;

@Repository
public class DataJpaRestaurantRepository {

    private final RestaurantRepository repository;

    @Autowired
    public DataJpaRestaurantRepository(RestaurantRepository repository) {
        this.repository = repository;
    }


    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public void inputNewRestaurant(Restaurant restaurant){
        repository.save(restaurant);
    }

    public void setNewMenu(int restaurantId, List<Dish> menu) {
        // нужен отдельный репозиторий для таблицы с меню
//        Menu newMenu = new Menu();
//        newMenu.setDishes(Arrays.asList(dishes));
        Restaurant restaurant = repository.getOne(restaurantId);
        restaurant.setMenu(menu);

    }

//    public void updateMenu(int restaurantId, Menu menu) {
//        Restaurant restaurant = repository.getOne(restaurantId);
//    }

    public void updateRating(int restaurantId, int rating) {
        repository.updateRating(restaurantId, rating);
    }

}
