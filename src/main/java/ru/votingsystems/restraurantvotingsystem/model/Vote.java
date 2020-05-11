package ru.votingsystems.restraurantvotingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_votes")
public class Vote extends AbstractBaseEntity {

    @NotNull
    @Column(name = "vote_date")
    private LocalDateTime voteDate;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @NotNull
    @Column(name = "restaurant_Id")
    private Integer restaurantId;

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @CollectionTable(name = "restaurant_menu", joinColumns = @JoinColumn(name = "vote_id"))
    @Column(name = "dish_info")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private List<String> menu;

    @JsonIgnoreProperties("votes")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Vote() {
    }

    public Vote(Vote v) {
        this(v.getId(), v.getVoteDate(), v.getRestaurantName(), v.getMenu(), v.getUser(), v.getRestaurantId());
    }

    public Vote(Integer id, LocalDateTime voteDate, String restaurantName, List<String> menu, User user, int restaurantId) {
        super(id);
        this.voteDate = voteDate;
        this.restaurantName = restaurantName;
        this.menu = menu;
        this.user = user;
        this.restaurantId = restaurantId;
    }


    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDateTime voteDate) {
        this.voteDate = voteDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<String> getMenu() {
        return menu;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id + '\''  +
                ", restaurantId=" + restaurantId + '\''  +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
