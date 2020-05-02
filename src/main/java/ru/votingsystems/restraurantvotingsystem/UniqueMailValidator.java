package ru.votingsystems.restraurantvotingsystem;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.UserRepository;
import ru.votingsystems.restraurantvotingsystem.web.ExceptionInfoHandler;

@Component
public class UniqueMailValidator implements org.springframework.validation.Validator {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = ((User) target);
        User dbUser = repository.findUserByEmail(user.getEmail().toLowerCase());
        if (dbUser != null && !dbUser.getId().equals(user.getId())) {
            errors.rejectValue("email", ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL);
        }
    }
}
