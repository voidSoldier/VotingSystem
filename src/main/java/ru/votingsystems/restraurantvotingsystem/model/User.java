package ru.votingsystems.restraurantvotingsystem.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

public class User {

    private String name;
    private String email;
    private String password;
    private Date registered;
    private LocalDateTime votingTime;
    // обнуление после 24 часов!
    private boolean hasVoted = false;
    private int ratedRestaurant;

    public LocalDateTime getVotingTime() {
        return votingTime;
    }

    public void setVotingTime(LocalDateTime votingTime) {
        this.votingTime = votingTime;
    }

    public int getRatedRestaurant() {
        return ratedRestaurant;
    }

    public void setRatedRestaurant(int ratedRestaurant) {
        this.ratedRestaurant = ratedRestaurant;
    }

    private Set<Role> roles;
    private boolean enabled = true;

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
}
