package ru.votingsystems.restraurantvotingsystem.model;

import java.util.List;

public class Restaurant extends AbstractBaseEntity {
    private String name;
    private List<Menu> menus;  // private Map<Integer, List<Dish>> menus;  ---> Integer is for menu_id
    private int rating;
}
