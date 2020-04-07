package ru.votingsystems.restraurantvotingsystem.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Set;

public class User extends AbstractNamedEntity {

    private String name;
    private String email;
    private String password;
    private Date registered = new Date();
    private LocalDateTime votingDate;
    // обнуление после 24 часов!
    private boolean hasVoted = false;
    private Set<Role> roles;
    private boolean enabled = true; // что это???

    public void vote() throws IllegalAccessException {

//        LocalDateTime nowTime = LocalDateTime.now();

//        LocalDateTime newTime = LocalDateTime.of(votingDate.toLocalDate(), nowTime.toLocalTime()).withHour(11); // та же дата, но с 11 часами дня

        if (votingDate == null) { // hasn't voted ever
            votingDate = LocalDateTime.now();
            hasVoted = true;
        } else if (votingDate != null && (LocalDateTime.now().getHour() - votingDate.getHour() >= 0)) { // voted yesterday
            hasVoted = false;
        } else if (votingDate.getHour() < 11) { // has voted today; should be before 11 a.m. to change their vote
            votingDate = LocalDateTime.now();
        } else {
            throw new IllegalAccessException("You cannot change your vote!");
        }
    }
}
