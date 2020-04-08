package ru.votingsystems.restraurantvotingsystem.model;

import java.util.List;

public class Restaurant extends AbstractBaseEntity {
    private String name; // OR Id?
    private List<Menu> menus;
    private int rating;
}
