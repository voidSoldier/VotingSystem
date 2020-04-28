package ru.votingsystems.restraurantvotingsystem.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.votingsystems.restraurantvotingsystem.RTestData;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class InMemoryRestaurantRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRestaurantRepository.class);

    private InMemoryBaseRepository<Restaurant> restaurants = new InMemoryBaseRepository<>();

    {
        RTestData.RESTAURANTS.forEach(restaurant -> restaurants.save(restaurant));
    }


//    @Override
    public Restaurant save(Restaurant restaurant, List<Dish> menu) {
        Objects.requireNonNull(restaurant, "Restaurant must not be null");
        restaurant.setMenu(menu);
        return restaurants.save(restaurant);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");
    }


    public boolean delete(int restaurantId) {
        return restaurants.delete(restaurantId);
    }


    public Restaurant get(int restaurantId) {
       return restaurants.get(restaurantId);
    }


    public List<Restaurant> getAll(int userId) {
        return new ArrayList<>(restaurants.getCollection());
    }


}