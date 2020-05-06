package ru.votingsystems.restraurantvotingsystem;

import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.votingsystems.restraurantvotingsystem.model.AbstractBaseEntity.START_SEQ;

public class RTestData {


    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "rating", "menu");
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsComparator(Dish.class, "restaurant");

    public static final int RESTAURANT1_ID = START_SEQ + 2;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "MamaS House");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT1_ID + 1, "My own Company");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT1_ID + 2, "HoundS Pit");

    static {
        RESTAURANT1.setMenu(Arrays.asList(
                new Dish(RESTAURANT1_ID + 3, "soup", 10.99, RESTAURANT1),
                new Dish(RESTAURANT1_ID + 4, "roast beef", 13.02, RESTAURANT1),
                new Dish(RESTAURANT1_ID + 5, "fried eggs", 11.00, RESTAURANT1),
                new Dish(RESTAURANT1_ID + 6, "muffin", 6.33, RESTAURANT1)));
        RESTAURANT1.setRating(10);

        RESTAURANT2.setMenu(Arrays.asList(
                new Dish(RESTAURANT1_ID + 7, "pancakes", 5.10, RESTAURANT2),
                new Dish(RESTAURANT1_ID + 8, "ice cream", 12.10, RESTAURANT2),
                new Dish(RESTAURANT1_ID + 9, "vanilla cake", 17.00, RESTAURANT2)));
        RESTAURANT2.setRating(7);

        RESTAURANT3.setMenu(Arrays.asList(
                new Dish(RESTAURANT1_ID + 10, "cheeseburger", 7.55, RESTAURANT3),
                new Dish(RESTAURANT1_ID + 11, "pizza", 15.00, RESTAURANT3),
                new Dish(RESTAURANT1_ID + 12, "lasagna", 14.20, RESTAURANT3)));
        RESTAURANT1.setRating(5);
    }


    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT1, RESTAURANT2, RESTAURANT3);

    public static Restaurant getNew() {
//        Restaurant newRestaurant = new Restaurant(null, "New Restaurant");
//        newRestaurant.setMenu(Collections.singletonList(new Dish(null, "new dish", 0, newRestaurant)));
//        return newRestaurant;
        return new Restaurant(null, "NEW", new ArrayList<>());
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated Restaurant");
    }
}


