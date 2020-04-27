package ru.votingsystems.restraurantvotingsystem.repository;

import org.springframework.stereotype.Repository;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;

import java.util.List;

@Repository
public class RestaurantRepository {
//@Autowired
    private final CrudRestaurantRepository repository;


    public RestaurantRepository(CrudRestaurantRepository repository) {
        this.repository = repository;
    }


    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

//    public Restaurant update(Restaurant restaurant) {
//        return repository.save(restaurant);
//    }

    public Restaurant save(Restaurant restaurant){
        return repository.save(restaurant);
    }

    public void setNewMenu(int restaurantId, List<Dish> menu) {

//        newMenu.setDishes(Arrays.asList(dishes));
//        Restaurant restaurant = repository.getOne(restaurantId);
//        restaurant.setMenu(menu);
        repository.updateMenu(restaurantId, menu);

    }

//    public void updateMenu(int restaurantId, Menu menu) {
//        Restaurant restaurant = repository.getOne(restaurantId);
//    }

    public void updateRating(int restaurantId, int rating) {
        repository.updateRating(restaurantId, rating);
    }



    /*
     * WILL THEY WORK??? F-ING SPRING MAGIC!
     */
    public void updateRestaurantMenuById(int id, List<Dish> menu) {
        repository.updateRestaurantMenuById(id, menu);
    }

    public void updateRestaurantRatingById(int id, int rating) {
        repository.updateRestaurantRatingById(id, rating);
    }

}
