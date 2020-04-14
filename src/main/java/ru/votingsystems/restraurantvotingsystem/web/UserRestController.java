package ru.votingsystems.restraurantvotingsystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.service.RestaurantService;


@Controller
public class UserRestController {

    @Autowired
    private RestaurantService service;

    public void vote(User user, int restaurantId) throws IllegalAccessException {  // userID instaed of User object?????

            service.voteForRestaurant(user, restaurantId);
        }

//            if (votingDate == null) { // hasn't voted ever
//                votingDate = LocalDateTime.now();
//                hasVoted = true;
//            } else if (votingDate != null && (LocalDateTime.now().getHour() - votingDate.getHour() >= 0)) { // voted yesterday
//                hasVoted = false;
//            } else if (votingDate.getHour() < 11) { // has voted today; should be before 11 a.m. to change their vote
//                votingDate = LocalDateTime.now();
//            } else {
//                throw new IllegalAccessException("You cannot change your vote!");
//            }
    }

