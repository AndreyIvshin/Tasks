package com.epam.clothshop.api;

import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.model.User;
import com.epam.clothshop.security.JwtGenerator;
import com.epam.clothshop.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.Optional;

import static com.epam.clothshop.api.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void loginWithoutParameters() throws Exception {
        mockMvc
                .perform(post(LOGIN_PATH))
                .andDo(print())
                .andExpect(status().isBadRequest());
        mockMvc
                .perform(post(LOGIN_PATH)
                        .param(USERNAME, "user"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        mockMvc
                .perform(post(LOGIN_PATH)
                        .param(PASSWORD, "user"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginWithParameters() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(post(LOGIN_PATH)
                        .param(USERNAME, "user")
                        .param(PASSWORD, "user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(cookie().exists(JWT))
                .andExpect(cookie().maxAge(JWT, 300))
                .andExpect(cookie().httpOnly(JWT, true))
                .andExpect(cookie().path(JWT, "/"))
                .andReturn().getResponse();

        Cookie cookie = response.getCookie(JWT);
        Long id = jwtGenerator.parseCookie(cookie).getId();
        Optional<User> optional = userService.findById(id);
        assertTrue(optional.isPresent());
        assertEquals("user", optional.get().getUsername());
        assertTrue(passwordEncoder.matches("user", optional.get().getPassword()));

        String json = response.getContentAsString();
        UserMapper.UserFull result = objectMapper().readValue(json, UserMapper.UserFull.class);
        UserMapper.UserFull origin = userMapper.mapFull(optional.get());
        assertTrue(origin.equals(result));
    }

    @Test
    void loginWhileAlreadyLoggedIn() throws Exception {
        Cookie cookie = mockMvc
                .perform(post(LOGIN_PATH)
                        .param(USERNAME, "user")
                        .param(PASSWORD, "user"))
                .andReturn()
                .getResponse()
                .getCookie(JWT);
        mockMvc
                .perform(post(LOGIN_PATH)
                        .param(USERNAME, "user")
                        .param(PASSWORD, "user")
                        .cookie(cookie))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void logoutWhileLoggedIn() throws Exception {
        Cookie cookie = mockMvc
                .perform(post(LOGIN_PATH)
                        .param(USERNAME, "user")
                        .param(PASSWORD, "user"))
                .andReturn()
                .getResponse()
                .getCookie(JWT);
        mockMvc
                .perform(post(LOGOUT_PATH)
                        .cookie(cookie))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(cookie().exists(JWT))
                .andExpect(cookie().maxAge(JWT, 0))
                .andExpect(cookie().httpOnly(JWT, true))
                .andExpect(cookie().path(JWT, "/"))
                .andExpect(cookie().value(JWT, ""));
    }

    @Test
    void logoutWhileNotLoggedIn() throws Exception {
        mockMvc
                .perform(post(LOGOUT_PATH))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void accessDenied() throws Exception {
        mockMvc
                .perform(post(DENIED_PATH))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}