package ru.votingsystems.restraurantvotingsystem.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.votingsystems.restraurantvotingsystem.RTestData;
import ru.votingsystems.restraurantvotingsystem.UTestData;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryRestaurantRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRestaurantRepository.class);

    // Map  userId -> mealRepository
    private Map<Integer, InMemoryBaseRepository<Restaurant>> usersMealsMap = new ConcurrentHashMap<>();

    {
        var userMeals = new InMemoryBaseRepository<Restaurant>();
        RTestData.RESTAURANTS.forEach(meal -> userMeals.map.put(meal.getId(), meal));
        usersMealsMap.put(UTestData.USER_ID, userMeals);
    }


//    @Override
    public Restaurant save(Restaurant meal, int userId) {
        Objects.requireNonNull(meal, "meal must not be null");
        var meals = usersMealsMap.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        return meals.save(meal);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");
    }

//    @Override
    public boolean delete(int id, int userId) {
        var meals = usersMealsMap.get(userId);
        return meals != null && meals.delete(id);
    }

//    @Override
    public Restaurant get(int id, int userId) {
        var meals = usersMealsMap.get(userId);
        return meals == null ? null : meals.get(id);
    }


////    @Override
//    public List<Restaurant> getAll(int userId) {
//        return getAllFiltered(userId, meal -> true);
//    }


}