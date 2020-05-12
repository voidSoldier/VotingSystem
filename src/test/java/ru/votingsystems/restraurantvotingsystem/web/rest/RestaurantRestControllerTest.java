package ru.votingsystems.restraurantvotingsystem.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.votingsystems.restraurantvotingsystem.RestaurantTestData;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;
import ru.votingsystems.restraurantvotingsystem.service.RestaurantService;
import ru.votingsystems.restraurantvotingsystem.util.exception.NotFoundException;
import ru.votingsystems.restraurantvotingsystem.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.votingsystems.restraurantvotingsystem.RestaurantTestData.*;
import static ru.votingsystems.restraurantvotingsystem.TestUtil.readFromJson;
import static ru.votingsystems.restraurantvotingsystem.TestUtil.userAuth;
import static ru.votingsystems.restraurantvotingsystem.UserTestData.ADMIN;
import static ru.votingsystems.restraurantvotingsystem.UserTestData.USER;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantService service;

    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(ADMIN))
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = readFromJson(action, Restaurant.class);
        int newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID)
                .with(userAuth(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userAuth(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANTS)); // RESTAURANT_TO_MATCHER ????
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userAuth(ADMIN)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(updated, service.get(RESTAURANT1_ID));
    }

    @Test
    void updateWithNewMenu() throws Exception {
        List<Dish> menu = RESTAURANT2.getMenu();

        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(menu))
                .with(userAuth(ADMIN)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(service.get(RESTAURANT1_ID).getMenu(), menu);
    }


    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID)
                .with(userAuth(ADMIN)))
                .andExpect(status().isNoContent());

        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }


    @Test
    void voteTimeoutExpired() throws Exception {
        User user = new User(USER);
        user.setVoted(true);
        user.setVotingTime(LocalDateTime.now().minusDays(2));

        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(user))
                .with(userAuth(USER)))
                .andExpect(status().isOk());
    }

    @Test
    void voteTimeoutNotExpired() throws Exception {
        User user = new User(USER);
        user.setVoted(true);
        user.setVotingTime(LocalDateTime.now());

        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(user))
                .with(userAuth(USER)))
                .andExpect(status().isConflict());
    }


    @Test
    void voteFirstTime() throws Exception {
        User user = new User(USER);

        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(user))
                .with(userAuth(USER)))
                .andExpect(status().isOk());
    }
}
