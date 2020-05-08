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
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantService(RestaurantRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant doesn't exist."));
    }

    public Restaurant getWithMenu(int id) {
        return repository.getWithMenu(id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public List<Restaurant> getAllWithMenu() {
        return repository.getAllWithMenu();
    }

    public void update(Restaurant restaurant) {
        repository.save(restaurant);
    }

    public boolean delete(int id) {
        int result = repository.delete(id);
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
        repository.save(restaurant); // needed?
//        repository.updateMenu(restaurantId, dishes);
    }

//    private void changeRating(int restaurantId, boolean increase) {
//        Restaurant restaurant = repository.findById(restaurantId).orElse(null);
//
//        if (restaurant == null) throw new NotFoundException("Restaurant doesn't exist.");
//        else {
//            int rating = restaurant.getRating();
//            repository.updateRating(restaurantId, increase ? ++rating : --rating);
//        }
//    }

//    private void increaseRating(int restaurantId) {
//        Restaurant restaurant = repository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant doesn't exist."));
//
//            int rating = restaurant.getRating();
//            setRating(restaurantId, ++rating);
//
//    }
//    private void decreaseRating(int restaurantId) {
//        Restaurant restaurant = repository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant doesn't exist."));
//
//            int rating = restaurant.getRating();
//            setRating(restaurantId, --rating);
//    }

    public void setRating(int restaurantId, int newRating) {
        repository.updateRating(restaurantId, newRating);
    }


    @Transactional
    public void voteForRestaurant(User user, int restaurantId) {
        LocalDateTime votingTime = user.getVotingTime();
        LocalDateTime nowVoting = LocalDateTime.now();

        List<Integer> allRestaurants = user.getRatedRestaurants();

        if (!user.isVoted() ||
                nowVoting.minusDays(1).compareTo(votingTime) >= 0) {
            // одним запросом????
//            setRating(restaurantId, get(restaurantId).getRating() + 1);

            allRestaurants.add(restaurantId);

            // тот же день, передумал
        } else if (votingTime.toLocalTime().isBefore(LocalTime.of(11, 0))) {
            int oldRatedRestaurant = allRestaurants.get(allRestaurants.size() - 1);

            List<Restaurant> restaurants = repository.getOldAndNew(oldRatedRestaurant, restaurantId);
            Restaurant oldR = null;
            Restaurant newR = null;
            for (Restaurant r : restaurants) {
                if (r.getId() == oldRatedRestaurant) oldR = r;
                else if (r.getId() == restaurantId) newR = r;
            }
            assert oldR != null;
            int oldRating = oldR.getRating();
            oldR.setRating(--oldRating);

            assert newR != null;
            int newRating = newR.getRating();
            newR.setRating(++newRating);

//            setRating(oldRatedRestaurant, get(oldRatedRestaurant).getRating() - 1);
//            setRating(restaurantId, get(restaurantId).getRating() + 1);

//            decreaseRating(oldRatedRestaurant);
//            increaseRating(restaurantId);
//
//            allRestaurants.set(allRestaurants.size() - 1, restaurantId);

        } else {
            throw new VotingTimeoutNotExpiredException("You cannot vote now! \r\nPlease wait for voting timeout to expire.");
        }

//        user.setRatedRestaurants(allRestaurants); // needed?
//        userRepository.save(user);   // needed?
    }
}
