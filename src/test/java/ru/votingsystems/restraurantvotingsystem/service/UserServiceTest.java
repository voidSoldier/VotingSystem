package ru.votingsystems.restraurantvotingsystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.votingsystems.restraurantvotingsystem.model.Role;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.UserRepository;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.votingsystems.restraurantvotingsystem.UTestData.*;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;
    @Autowired
    private UserRepository repository;



    void create() throws Exception {
        User newUser = getNew();
        User created = service.create(new User(newUser));
        int newId = created.getId();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }


//    @Test
//    void createWithException() throws Exception {
//        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.USER)), ConstraintViolationException.class);
//        validateRootCause(() -> service.create(new User(null, "User", "  ", "password", Role.USER)), ConstraintViolationException.class);
//        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.USER)), ConstraintViolationException.class);
//        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", 9, true, new Date(), Set.of())), ConstraintViolationException.class);
//        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", 10001, true, new Date(), Set.of())), ConstraintViolationException.class);
//    }

    @Test
    void getAll() throws Exception {
        List<User> all = service.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }

    @Test
    void get() throws Exception {
        User user = service.get(ADMIN_ID);
        USER_MATCHER.assertMatch(user, ADMIN);
    }

    @Test
    void getWithRestaurants() throws Exception {
        User user = service.getWithRestaurants(ADMIN_ID);
//        USER_MATCHER_WITH_RESTAURANTS.assertMatch(user, ADMIN);
        assertEquals(ADMIN.getVotes(), user.getVotes());
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, ADMIN);
    }


    @Test
    void update() throws Exception {
        User updated = getUpdated();
        service.update(new User(updated));
        USER_MATCHER.assertMatch(service.get(USER_ID), updated);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_ID);
        Assertions.assertNull(repository.findById(USER_ID).orElse(null));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(1));
    }

    @Test
    void enable() throws Exception {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }

}