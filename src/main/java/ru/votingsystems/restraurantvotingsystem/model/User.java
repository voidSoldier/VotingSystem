package ru.votingsystems.restraurantvotingsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractBaseEntity implements Serializable, AbstractUser {
    private static final long serialVersionUID = 1L;


    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered;

    @Column(name = "voting_time")
    private LocalDateTime votingTime;

    @Column(name = "has_voted", nullable = false, columnDefinition = "bool default false")
    private boolean voted = false;


    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @BatchSize(size = 200)
    private List<Vote> votes;


    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;


    public User() {
    }

    public User(User u) {
        super(u.getId());
        this.name = u.getName();
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.registered = u.getRegistered();
        this.enabled = u.isEnabled();
        setRoles(u.getRoles());
        this.votingTime = u.getVotingTime();
        this.voted = u.isVoted();
        setVotes(getVotes());
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, new Date(), true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, Date registered, boolean enabled, Collection<Role> roles) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public List<Vote> getVotes() {
        return votes == null ? new ArrayList<>() : votes;

    }

    public void setVotes(List<Vote> v) {
        if (votes == null) {
            votes = new ArrayList<>(v);
        } else {
            votes.addAll(v);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getVotingTime() {
        return votingTime;
    }

    public void setVotingTime(LocalDateTime votingTime) {
        this.votingTime = votingTime;
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

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", registered='" + registered + '\'' +
                ", roles='" + roles + '\'' +
                ", votes='" + votes + '\'' +
                '}';
    }
}


