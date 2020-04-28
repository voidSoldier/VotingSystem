package ru.votingsystems.restraurantvotingsystem.web;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.votingsystems.restraurantvotingsystem.TestUtil.userAuth;
import static ru.votingsystems.restraurantvotingsystem.UTestData.ADMIN;
import static ru.votingsystems.restraurantvotingsystem.UTestData.USER;

class RootControllerTest extends AbstractControllerTest {

    /*
    ПРОВЕРИТЬ!!!!!
     */
    @Test
    void getUsers() throws Exception {
        perform(get("/users")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    void unAuth() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void getRestaurants() throws Exception {
        perform(get("/restaurants")
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/restaurants.jsp"));
    }
}