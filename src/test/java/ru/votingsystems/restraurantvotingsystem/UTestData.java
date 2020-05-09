package ru.votingsystems.restraurantvotingsystem;

import ru.votingsystems.restraurantvotingsystem.model.Role;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.model.Vote;
import ru.votingsystems.restraurantvotingsystem.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static ru.votingsystems.restraurantvotingsystem.model.AbstractBaseEntity.START_SEQ;

public class UTestData {

    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "registered", "ratedRestaurants", "password");
//        public static TestMatcher<User> USER_MATCHER_WITH_RESTAURANTS = TestMatcher.usingFieldsComparator(User.class, "registered","password");

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    static {
        addVotes();
    }

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.USER));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

    public static void addVotes() {
        USER.setVotes(Arrays.asList(
                new Vote(null, LocalDateTime.of(2020, 1, 30, 10, 0),
                        "MamaS House", new ArrayList<>(), USER, 100002),
                new Vote(null, LocalDateTime.of(2020, 1, 31, 0, 0),
                        "HoundS Pit", new ArrayList<>(), USER, 100004)));
        ADMIN.setVotes(Collections.singletonList(
                new Vote(null, LocalDateTime.of(2020, 1, 30, 20, 0),
                        "My own Company", new ArrayList<>(), USER, 100003)));
    }
}


