package ru.votingsystems.restraurantvotingsystem.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.votingsystems.restraurantvotingsystem.model.Role;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.to.UserTo;


public class UserUtil {

    public static User createNewFromTo(UserTo userTo) {
        User newUser = new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
        newUser.setVotes(userTo.getVotes());
        return newUser;
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getVotes());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        user.setVotes(userTo.getVotes());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}