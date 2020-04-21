package ru.votingsystems.restraurantvotingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.DataJpaRestaurantRepository;
import ru.votingsystems.restraurantvotingsystem.repository.DataJpaUserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private DataJpaRestaurantRepository repository;
    private DataJpaUserRepository userRepository;

    public void inputNewRestaurant(Restaurant newRestaurant) {
        repository.inputNewRestaurant(newRestaurant);
    }

    public void setNewMenu(int restaurantId, Dish... dishes) {
        List menu = Arrays.asList(dishes);
        repository.setNewMenu(restaurantId, menu);
    }

//    public void updateMenuOfTheDay(int restaurantId, Menu menu) {}

    public void increaseRating(int restaurantId) {
        int rating = repository.get(restaurantId).getRating();
        repository.updateRating(restaurantId, ++rating);
    }

    public void decreaseRating(int restaurantId) {
        int rating = repository.get(restaurantId).getRating();
        repository.updateRating(restaurantId, --rating);
    }

    /*
    If user votes again the same day:

    If it is before 11:00 we asume that he changed his mind.
    If it is after 11:00 then it is too late, vote can't be changed

     */
    public void voteForRestaurant (User user, int restaurantId) {

        LocalDateTime votingTime = user.getVotingTime();
        LocalDateTime nowVoting = LocalDateTime.now();

        // add posssibility to change vote
        if (!user.isVoted()) {

            increaseRating(restaurantId);

            user.setRatedRestaurant(restaurantId);
            userRepository.update(user.getId(), user);

        } else if (nowVoting.minusDays(1).equals(votingTime)) {

            int oldRatedRestaurant = user.getRatedRestaurant();
            decreaseRating(oldRatedRestaurant);
            increaseRating(restaurantId);

            user.setRatedRestaurant(restaurantId);
            userRepository.update(user.getId(), user);
        }
    }
}
