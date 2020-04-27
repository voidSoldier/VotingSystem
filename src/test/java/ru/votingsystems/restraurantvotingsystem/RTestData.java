package ru.votingsystems.restraurantvotingsystem;

import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static ru.votingsystems.restraurantvotingsystem.model.AbstractBaseEntity.START_SEQ;

public class RTestData {


    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsComparator(Restaurant.class, "rating");

    public static final int RESTAURANT1_ID = START_SEQ + 2;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "MamaS House");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT1_ID + 1, "My own Company");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT1_ID + 2, "HoundS Pit");

    static {
        RESTAURANT1.setMenu(Arrays.asList(
                new Dish(null, "soup", 10.99, RESTAURANT1),
                new Dish(null, "roast beef", 13.02, RESTAURANT1),
                new Dish(null, "fried eggs", 11.00, RESTAURANT1),
                new Dish(null, "muffin", 6.33, RESTAURANT1)));
        RESTAURANT1.setRating(10);

        RESTAURANT2.setMenu(Arrays.asList(
                new Dish(null, "pancakes", 5.10, RESTAURANT2),
                new Dish(null, "ice cream", 12.10, RESTAURANT2),
                new Dish(null, "vanilla cake", 17.00, RESTAURANT2)));
        RESTAURANT2.setRating(7);

        RESTAURANT3.setMenu(Arrays.asList(
                new Dish(null, "cheeseburger", 7.55, RESTAURANT3),
                new Dish(null, "pizza", 15.00, RESTAURANT3),
                new Dish(null, "lasagna", 14.20, RESTAURANT3)));
        RESTAURANT1.setRating(5);
    }


    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT1, RESTAURANT2, RESTAURANT3);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated Restaurant");
    }
}

