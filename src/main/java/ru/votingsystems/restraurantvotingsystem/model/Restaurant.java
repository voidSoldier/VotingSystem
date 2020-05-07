package ru.votingsystems.restraurantvotingsystem.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@JsonIgnoreProperties(value = "menu")
public class Restaurant extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    private String name;


    //    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @BatchSize(size = 200)
    private List<Dish> menu;


    @Column(name = "rating")
    private int rating = 0;


//    @ManyToMany
//    @JoinTable(name = "rated_restaurants",
//            joinColumns = {@JoinColumn(name = "restaurant_id", referencedColumnName = "id"),
//                    @JoinColumn(name = "restaurant_name", referencedColumnName = "name")},
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
//    private List<User> users;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Restaurant(Integer id, String name, List<Dish> menu) {
        super(id);
        this.name = name;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getMenu() {
        if (menu == null) return new ArrayList<>();
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menu='" + menu + '\'' +
                '}';
    }
}
