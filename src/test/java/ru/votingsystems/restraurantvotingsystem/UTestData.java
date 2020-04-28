package ru.votingsystems.restraurantvotingsystem;

import ru.votingsystems.restraurantvotingsystem.model.Role;
import ru.votingsystems.restraurantvotingsystem.model.User;

import java.util.Collections;

import static ru.votingsystems.restraurantvotingsystem.model.AbstractBaseEntity.START_SEQ;

public class UTestData {

        public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "registered", "restaurants");

        public static final int USER_ID = START_SEQ;
        public static final int ADMIN_ID = START_SEQ + 1;

        public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
        public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

        public static User getNew() {
            return new User(null, "New", "new@gmail.com", "newPass", Role.USER);
        }

        public static User getUpdated() {
            User updated = new User(USER);
            updated.setName("UpdatedName");
            updated.setRoles(Collections.singletonList(Role.ADMIN));
            return updated;
        }
    }


