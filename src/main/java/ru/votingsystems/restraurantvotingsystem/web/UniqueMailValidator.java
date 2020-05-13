package ru.votingsystems.restraurantvotingsystem.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.votingsystems.restraurantvotingsystem.model.AbstractUser;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.UserRepository;
import ru.votingsystems.restraurantvotingsystem.to.UserTo;
import ru.votingsystems.restraurantvotingsystem.util.UserUtil;

@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return AbstractUser.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (target instanceof User) ?
                (User) target :
                UserUtil.updateFromTo(new User(), (UserTo) target);

        User dbUser = repository.findUserByEmail(user.getEmail().toLowerCase());
        if (dbUser != null && !dbUser.getId().equals(user.getId())) {
            errors.rejectValue("email", "User with this email already exists");
        }
    }
}
