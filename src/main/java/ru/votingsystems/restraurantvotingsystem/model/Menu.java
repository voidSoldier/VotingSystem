package ru.votingsystems.restraurantvotingsystem.model;

import java.util.List;

public class Menu {

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    private List<Dish> dishes;
}
