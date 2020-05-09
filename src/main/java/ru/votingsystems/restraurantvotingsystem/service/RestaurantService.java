package ru.votingsystems.restraurantvotingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.model.Vote;
import ru.votingsystems.restraurantvotingsystem.repository.RestaurantRepository;
import ru.votingsystems.restraurantvotingsystem.repository.UserRepository;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;
import ru.votingsystems.restraurantvotingsystem.util.exception.VotingTimeoutNotExpiredException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void voteForRestaurant(User user, int restaurantId) {
        LocalDateTime votingTime = user.getVotingTime();
        LocalDateTime nowVoting = LocalDateTime.now();

        // voting first time
        // or on the next day
        if (!user.isVoted() ||
                nowVoting.minusDays(1).compareTo(votingTime) >= 0) {
            repository.incrementRating(restaurantId);
//
            // voting the same day again to change the vote
            // possible if it's before 11:00 a.m.
        } else if (votingTime.toLocalTime().isBefore(LocalTime.of(11, 0))) {
            int lastRatedRestaurantId = getLastRatedRestaurantId(user, votingTime);

            repository.decrementRating(lastRatedRestaurantId);
            repository.incrementRating(restaurantId);

            removeVote(user, votingTime);
        } else {
            throw new VotingTimeoutNotExpiredException("You cannot vote now! \r\nPlease wait for voting timeout to expire.");
        }

        user.setVotingTime(nowVoting);
        saveVote(restaurantId, nowVoting, user);

//        user.setRatedRestaurants(allRestaurants); // needed?
//        userRepository.save(user);   // needed?
    }

    private void saveVote(int restaurantId, LocalDateTime voteTime, User user) {
        List<Vote> votes = user.getVotes();
        Restaurant restaurant = get(restaurantId);

        List<String> menu = restaurant.getMenu()
                .stream()
                .map(d -> d.getName() + " - " + d.getPrice())
                .collect(Collectors.toList());

        votes.add(new Vote(null, voteTime, restaurant.getName(), menu, user, restaurant.getId()));

    }

    private int getLastRatedRestaurantId(User user, LocalDateTime voteTime) {
        return user.getVotes()
                .stream()
                .filter(v -> v.getVoteDate().equals(voteTime))
                .map(Vote::getRestaurantId)
                .findAny().orElse(0);
    }

    private void removeVote(User user, LocalDateTime voteTime) {
        user.setVotes(user.getVotes()
                .stream()
                .filter(v -> v.getVoteDate().equals(voteTime))
                .collect(Collectors.toList()));
    }
}
