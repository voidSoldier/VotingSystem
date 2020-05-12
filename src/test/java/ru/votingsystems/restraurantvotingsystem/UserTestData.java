package ru.votingsystems.restraurantvotingsystem;

import ru.votingsystems.restraurantvotingsystem.model.Role;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.web.json.JsonUtil;

import java.util.Collections;

import static ru.votingsystems.restraurantvotingsystem.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "registered", "votes", "password");

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

//    public static final Vote VOTE1 = new Vote(null, LocalDateTime.of(2020, 1, 30, 10, 0),
//            RESTAURANT1.getName(), new ArrayList<>(), USER, RESTAURANT1_ID);
//    public static final Vote VOTE2 = new Vote(null, LocalDateTime.of(2020, 1, 31, 0, 0),
//            RESTAURANT3.getName(), new ArrayList<>(), USER, RESTAURANT3.getId());
//    public static final Vote VOTE3 = new Vote(null, LocalDateTime.of(2020, 1, 30, 20, 0),
//            RESTAURANT2.getName(), new ArrayList<>(), USER, RESTAURANT2.getId());

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
}


