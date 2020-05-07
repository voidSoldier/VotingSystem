package ru.votingsystems.restraurantvotingsystem.web.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.repository.RestaurantRepository;
import ru.votingsystems.restraurantvotingsystem.repository.UserRepository;
import ru.votingsystems.restraurantvotingsystem.service.RestaurantService;
import ru.votingsystems.restraurantvotingsystem.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.votingsystems.restraurantvotingsystem.RTestData.*;
import static ru.votingsystems.restraurantvotingsystem.TestUtil.*;
import static ru.votingsystems.restraurantvotingsystem.UTestData.*;

public class SpecialControllerTest extends AbstractControllerTest {

    private static final String Restaurant_REST_URL = RestaurantRestController.REST_URL + '/';
    private static final String Admin_REST_URL = AdminRestController.REST_URL + '/';


    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserRepository repo1;

    @Autowired
    private RestaurantRepository repo2;


    @Test
    void getAllUsersWithRoles() throws Exception {
//      ResultActions actions = perform(MockMvcRequestBuilders.get(Admin_REST_URL)
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(USER_MATCHER.contentJson(ADMIN, USER));
//
//      List<User> result = readListFromJsonMvcResult(actions.andReturn(), User.class);
//        System.out.println(result);
//    }
        List<User> result = repo1.getAllWithRestaurants();
        List<User> users = Arrays.asList(USER, ADMIN);
        USER_MATCHER.assertMatch(result, users);

            Assertions.assertEquals(USER.getRatedRestaurants(), result.get(0).getRatedRestaurants());
            Assertions.assertEquals(ADMIN.getRatedRestaurants(), result.get(1).getRatedRestaurants());
    }

    @Test
    void getAllRestaurantsWithMenu() throws Exception {
        List<Restaurant> result = repo2.getAllWithMenu();
        RESTAURANT_MATCHER.assertMatch(RESTAURANTS, result);

        Assertions.assertEquals(RESTAURANT1.getMenu(), result.get(0).getMenu());
        Assertions.assertEquals(RESTAURANT2.getMenu(), result.get(1).getMenu());
        Assertions.assertEquals(RESTAURANT3.getMenu(), result.get(2).getMenu());

    }
}
