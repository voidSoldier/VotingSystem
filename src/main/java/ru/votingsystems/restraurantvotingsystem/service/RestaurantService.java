package ru.votingsystems.restraurantvotingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.CrudRestaurantRepository;
import ru.votingsystems.restraurantvotingsystem.repository.CrudUserRepository;
import ru.votingsystems.restraurantvotingsystem.util.exception.VotingTimeoutNotExpiredException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class RestaurantService {

//    @Autowired
//    private RestaurantRepository repository;
    private final CrudRestaurantRepository repository;
//    @Autowired
    private final CrudUserRepository userRepository;

    @Autowired
    public RestaurantService(CrudRestaurantRepository repository, CrudUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    public void update(Restaurant restaurant) {
        repository.save(restaurant);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public Restaurant create(Restaurant newRestaurant) {
        return repository.save(newRestaurant);
    }


    public void setNewMenu(int restaurantId, List<Dish> dishes) {
//        List menu = Arrays.asList(dishes);
        repository.updateMenu(restaurantId, dishes);
    }


    public void increaseRating(int restaurantId) {
        int rating = repository.findById(restaurantId).orElse(null).getRating();
        repository.updateRating(restaurantId, ++rating);
    }

    public void decreaseRating(int restaurantId) {
        int rating = repository.findById(restaurantId).orElse(null).getRating();
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

        // если не голосовал ни разу ИЛИ прошло уже 24 ч
        if (!user.isVoted() ||
                nowVoting.minusDays(1).compareTo(votingTime) >= 0) {
            increaseRating(restaurantId);

            // если прошли 24 часа
//        } else if (nowVoting.minusDays(1).compareTo(votingTime) >= 0) {  // как быть с минутами???
//            increaseRating(restaurantId);


            // тот же день, передумал
        } else if (votingTime.toLocalTime().isBefore(LocalTime.of(11, 0))) {

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
