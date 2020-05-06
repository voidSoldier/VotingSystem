package ru.votingsystems.restraurantvotingsystem.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.votingsystems.restraurantvotingsystem.model.AbstractBaseEntity;
import ru.votingsystems.restraurantvotingsystem.model.AbstractUser;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTo extends AbstractBaseEntity implements Serializable, AbstractUser {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    private String password;

    private List<Integer> ratedRestaurants;


    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password, List<Integer> restaurants) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.ratedRestaurants = restaurants;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public List<Integer> getRatedRestaurants() {
        return ratedRestaurants;
    }

    public void setRatedRestaurants(List<Integer> restaurants) {
        this.ratedRestaurants = restaurants;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", restaurants='" + ratedRestaurants + '\'' +
                '}';
    }
}
