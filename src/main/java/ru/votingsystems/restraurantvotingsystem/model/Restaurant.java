package ru.votingsystems.restraurantvotingsystem.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    private String name;


    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "dishes", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "dishes")
    @ElementCollection(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
//    @BatchSize(size = 200)
    private List<Integer> menu;


    @Column(name = "rating")
    private int rating = 0;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Restaurant(Integer id, String name, List<Integer> menu) {
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

    public List<Integer> getMenu() {
        return menu;
    }

    public void setMenu(List<Integer> menu) {
        this.menu = menu;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
