package ru.votingsystems.restraurantvotingsystem.service;

import org.springframework.stereotype.Service;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.DataJpaRestaurantRepository;
import ru.votingsystems.restraurantvotingsystem.repository.DataJpaUserRepository;
import ru.votingsystems.restraurantvotingsystem.util.exception.VotingTimeoutNotExpiredException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
public class RestaurantService {


    private DataJpaRestaurantRepository repository;
    private DataJpaUserRepository userRepository;

    public RestaurantService(DataJpaRestaurantRepository repository, DataJpaUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }


    public void inputNewRestaurant(Restaurant newRestaurant) {
        repository.inputNewRestaurant(newRestaurant);
    }


    public void setNewMenu(int restaurantId, Dish... dishes) {
        List menu = Arrays.asList(dishes);
        repository.setNewMenu(restaurantId, menu);
    }


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
    public void voteForRestaurant(User user, int restaurantId) {

        LocalDateTime votingTime = user.getVotingTime();
        LocalDateTime nowVoting = LocalDateTime.now();

        // если не голосовал ни разу
        if (!user.isVoted() ||
                nowVoting.minusDays(1).compareTo(votingTime) >= 0) {
            increaseRating(restaurantId);

            // если прошли 24 часа
//        } else if (nowVoting.minusDays(1).compareTo(votingTime) >= 0) {  // как быть с минутами???
//            increaseRating(restaurantId);


            // тот же день, передумал
        } else if (votingTime.toLocalTime().isBefore(LocalTime.of(11, 00))) {

            int oldRatedRestaurant = user.getRatedRestaurant();
            decreaseRating(oldRatedRestaurant);
            increaseRating(restaurantId);

        } else {
            throw new VotingTimeoutNotExpiredException("You cannot vote now! \r\nPlease wait for voting timeout to expire.");
        }

        user.setRatedRestaurant(restaurantId);
        userRepository.update(user.getId(), user);
    }
}
