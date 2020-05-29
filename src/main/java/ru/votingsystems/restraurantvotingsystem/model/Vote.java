package ru.votingsystems.restraurantvotingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "vote_date_unique_idx")})
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

    @NotBlank
    @Size(min = 5)
    @Column(name = "menu", nullable = false)
    private String menuAtTheDate;

    @JsonIgnoreProperties("votes")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Vote() {
    }

    public Vote(Vote v) {
        this(v.getId(), v.getVoteDate(), v.getRestaurantName(), v.getMenuAtTheDate(), v.getUser(), v.getRestaurantId());
    }


    public Vote(Integer id, LocalDateTime voteDate, String restaurantName, String menuAtTheDate, User user, int restaurantId) {
        super(id);
        this.voteDate = voteDate;
        this.restaurantName = restaurantName;
        this.menuAtTheDate = menuAtTheDate;
        this.user = user;
        this.restaurantId = restaurantId;
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getMenuAtTheDate() {
        return menuAtTheDate;
    }

    public void setMenuAtTheDate(String menuAtTheDate) {
        this.menuAtTheDate = menuAtTheDate;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @JsonIgnore
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

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id + '\'' +
                ", restaurantId=" + restaurantId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
