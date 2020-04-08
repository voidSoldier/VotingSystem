package ru.votingsystems.restraurantvotingsystem.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

public class User extends AbstractBaseEntity {

    private String name;
    private String email;
    private String password;
    private Date registered;
    private LocalDateTime votingDate;
    // обнуление после 24 часов!
    private boolean hasVoted = false;
    private Set<Role> roles;
    private boolean enabled = true; // что это???

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public LocalDateTime getVotingDate() {
        return votingDate;
    }

    public void setVotingDate(LocalDateTime votingDate) {
        this.votingDate = votingDate;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    //    public void vote() throws IllegalAccessException {

//        LocalDateTime nowTime = LocalDateTime.now();

//        LocalDateTime newTime = LocalDateTime.of(votingDate.toLocalDate(), nowTime.toLocalTime()).withHour(11); // та же дата, но с 11 часами дня

//        if (votingDate == null) { // hasn't voted ever
//            votingDate = LocalDateTime.now();
//            hasVoted = true;
//        } else if (votingDate != null && (LocalDateTime.now().getHour() - votingDate.getHour() >= 0)) { // voted yesterday
//            hasVoted = false;
//        } else if (votingDate.getHour() < 11) { // has voted today; should be before 11 a.m. to change their vote
//            votingDate = LocalDateTime.now();
//        } else {
//            throw new IllegalAccessException("You cannot change your vote!");
//        }
//    }
}
