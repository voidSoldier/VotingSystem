package ru.votingsystems.restraurantvotingsystem.model;

import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "menu_date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;


    public Dish() {
    }

    public Dish(Integer id, String name, double price, Restaurant restaurant) {
        super(id);
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
        menuDate = LocalDateTime.now().toLocalDate();
    }


    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menu_date) {
        this.menuDate = menu_date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        Dish that = (Dish) o;
        return name != null && name.equals(that.name)
                && price == that.price
                && restaurant != null && restaurant.equals(that.restaurant);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

}
