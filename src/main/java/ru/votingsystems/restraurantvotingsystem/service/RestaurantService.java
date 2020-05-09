package ru.votingsystems.restraurantvotingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.RestaurantRepository;
import ru.votingsystems.restraurantvotingsystem.repository.UserRepository;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;
import ru.votingsystems.restraurantvotingsystem.util.exception.VotingTimeoutNotExpiredException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant doesn't exist."));
    }

    public Restaurant getWithMenu(int id) {
        return repository.findRestaurantById(id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public List<Restaurant> getAllWithMenu() {
        return repository.findAll();
    }

    public void update(Restaurant restaurant) {
        repository.save(restaurant);
    }

    public boolean delete(int id) {
        int result = repository.deleteRestaurantById(id);
        if (result != 0) return true;
        else throw new NotFoundException("Restaurant doesn't exist.");
    }

    public Restaurant create(Restaurant newRestaurant) {
        return repository.save(newRestaurant);
    }

    @Transactional
    public void setNewMenu(int restaurantId, List<Dish> dishes) {
        Restaurant restaurant = get(restaurantId);
        restaurant.setMenu(dishes);
    }

//    @Transactional
//    public void voteForRestaurant(User user, int restaurantId) {
//        LocalDateTime votingTime = user.getVotingTime();
//        LocalDateTime nowVoting = LocalDateTime.now();
//
//        List<Integer> allRestaurants = user.getRatedRestaurants();
//
//        // voting first time
//        // or on the next day
//        if (!user.isVoted() ||
//                nowVoting.minusDays(1).compareTo(votingTime) >= 0) {
//            repository.incrementRating(restaurantId);
//            allRestaurants.add(restaurantId);
//
//            // voting the same day again to change the vote
//            // possible if it's before 11:00 a.m.
//        } else if (votingTime.toLocalTime().isBefore(LocalTime.of(11, 0))) {
//            int oldRatedRestaurant = allRestaurants.get(allRestaurants.size() - 1);
//            repository.decrementRating(oldRatedRestaurant);
//            repository.incrementRating(restaurantId);
//            allRestaurants.set(allRestaurants.size() - 1, restaurantId);
//        } else {
//            throw new VotingTimeoutNotExpiredException("You cannot vote now! \r\nPlease wait for voting timeout to expire.");
//        }
//
//        allRestaurants.add(restaurantId);
//        user.setVotingTime(nowVoting);
////        user.setRatedRestaurants(allRestaurants); // needed?
////        userRepository.save(user);   // needed?
//    }


    @Transactional
    public void voteForRestaurant(User user, int restaurantId) {
        LocalDateTime votingTime = user.getVotingTime();
        LocalDateTime nowVoting = LocalDateTime.now();

        // voting first time
        // or on the next day
        if (!user.isVoted() ||
                nowVoting.minusDays(1).compareTo(votingTime) >= 0) {
            repository.incrementRating(restaurantId);
            user.getVotes().put(nowVoting, restaurantId);

            // voting the same day again to change the vote
            // possible if it's before 11:00 a.m.
        } else if (votingTime.toLocalTime().isBefore(LocalTime.of(11, 0))) {
            int lastRatedRestaurant = user.getVotes().get(votingTime);
            repository.decrementRating(lastRatedRestaurant);
            repository.incrementRating(restaurantId);
            user.getVotes().remove(votingTime);
//            user.getVotes().put(nowVoting, restaurantId);
        } else {
            throw new VotingTimeoutNotExpiredException("You cannot vote now! \r\nPlease wait for voting timeout to expire.");
        }

        user.setVotingTime(nowVoting);
        user.getVotes().put(nowVoting, restaurantId);

//        user.setRatedRestaurants(allRestaurants); // needed?
//        userRepository.save(user);   // needed?
    }
}
