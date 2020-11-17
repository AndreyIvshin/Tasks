package com.epam.clothshop.api;

import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.mapper.UserMapper;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.User;
import com.epam.clothshop.security.Role;
import com.epam.clothshop.service.UserService;
import com.epam.clothshop.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.clothshop.api.TestUtils.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    void getUsers() throws Exception {
        List<User> users = userService.findAll();

        var origins = users.stream().map(userMapper::mapLite).collect(Collectors.toList());

        String json = mockMvc
                .perform(get(USERS)
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var results = objectMapper().readValue(json, new TypeReference<List<UserMapper.UserLite>>() {
        });

        assertTrue(results.containsAll(origins));
        assertTrue(origins.containsAll(results));
    }

    @Test
    void getUsersDenied() throws Exception {
        List<User> users = userService.findAll();

        var origins = users.stream().map(userMapper::mapLite).collect(Collectors.toList());

        mockMvc
                .perform(get(USERS)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getUser() throws Exception {
        User user = userService.findById(1L).get();

        var origin = userMapper.mapFull(user);

        String json = mockMvc
                .perform(get(USERS + "/" + user.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var result = objectMapper().readValue(json, UserMapper.UserFull.class);

        assertTrue(origin.equals(result));
    }

    @Test
    void getUserSameId() throws Exception {
        User user = userService.findById(2L).get();

        var origin = userMapper.mapFull(user);

        String json = mockMvc
                .perform(get(USERS + "/" + user.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var result = objectMapper().readValue(json, UserMapper.UserFull.class);

        assertTrue(origin.equals(result));
    }

    @Test
    void getUserDenied() throws Exception {
        User user = userService.findById(1L).get();

        var origin = userMapper.mapFull(user);

        mockMvc
                .perform(get(USERS + "/" + user.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void postUser() throws Exception {
        User user = createUser();

        String json = objectMapper().writeValueAsString(userMapper.mapToSave(user));

        MockHttpServletResponse response = mockMvc
                .perform(post(USERS)
                        .cookie(login("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        var result = objectMapper().readValue(response.getContentAsString(), UserMapper.UserFull.class);

        List<String> list = Arrays.asList(response.getHeader("Location").split("/"));
        Optional<User> optional = userService.findById(Long.valueOf(list.get(list.size() - 1)));

        assertTrue(optional.isPresent());

        var origin = userMapper.mapFull(optional.get());

        assertTrue(origin.equals(result));
    }

    @Test
    void postUserUnauthorized() throws Exception {
        User user = createUser();

        String json = objectMapper().writeValueAsString(userMapper.mapToSave(user));

        MockHttpServletResponse response = mockMvc
                .perform(post(USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        var result = objectMapper().readValue(response.getContentAsString(), UserMapper.UserFull.class);

        List<String> list = Arrays.asList(response.getHeader("Location").split("/"));
        Optional<User> optional = userService.findById(Long.valueOf(list.get(list.size() - 1)));

        assertTrue(optional.isPresent());

        var origin = userMapper.mapFull(optional.get());

        assertTrue(origin.equals(result));
    }

    @Test
    void postUserDenied() throws Exception {
        User user = createUser();

        String json = objectMapper().writeValueAsString(userMapper.mapToSave(user));

        mockMvc
                .perform(post(USERS)
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void putUser() throws Exception {
        User user = userService.findById(2L).get();

        var userToSave = userMapper.mapToSave(user);
        userToSave.setUsername("newUserUpdated");

        String json = objectMapper().writeValueAsString(userToSave);

        mockMvc
                .perform(put(USERS + "/" + user.getId())
                        .cookie(login("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<User> optional = userService.findById(user.getId());

        assertTrue(optional.isPresent());

        var origin = userMapper.mapFull(optional.get());

        assertTrue(origin.getUsername().equals("newUserUpdated"));
    }

    @Test
    void putUserSameId() throws Exception {
        User user = userService.findById(2L).get();

        var userToSave = userMapper.mapToSave(user);
        userToSave.setUsername("newUserUpdated");

        String json = objectMapper().writeValueAsString(userToSave);

        mockMvc
                .perform(put(USERS + "/" + user.getId())
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<User> optional = userService.findById(user.getId());

        assertTrue(optional.isPresent());

        var origin = userMapper.mapFull(optional.get());

        assertTrue(origin.getUsername().equals("newUserUpdated"));
    }

    @Test
    void putUserDenied() throws Exception {
        User user = userService.findById(1L).get();

        var userToSave = userMapper.mapToSave(user);
        userToSave.setUsername("newUserUpdated");

        String json = objectMapper().writeValueAsString(userToSave);

        mockMvc
                .perform(put(USERS + "/" + user.getId())
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void putUserDeniedOnSameIdRoleChange() throws Exception {
        User user = userService.findById(1L).get();

        var userToSave = userMapper.mapToSave(user);
        userToSave.setRole(Role.USER);

        String json = objectMapper().writeValueAsString(userToSave);

        mockMvc
                .perform(put(USERS + "/" + user.getId())
                        .cookie(login("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUser() throws Exception {
        User user = userService.findById(2L).get();

        mockMvc
                .perform(delete(USERS + "/" + user.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<User> optional = userService.findById(user.getId());

        assertFalse(optional.isPresent());
    }

    @Test
    void deleteUserDenied() throws Exception {
        mockMvc
                .perform(delete(USERS + "/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void putUserOrder() throws Exception {
        Order order = populateOrder();
        User user = userService.findById(2L).get();

        mockMvc
                .perform(put(USERS + "/" + user.getId() + "/orders/" + order.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<User> optional = userService.findById(user.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(user));
        assertTrue(optional.get().getOrders().contains(order));
    }

    @Test
    void putUserOrderSameId() throws Exception {
        Order order = populateOrder();
        User user = userService.findById(2L).get();

        mockMvc
                .perform(put(USERS + "/" + user.getId() + "/orders/" + order.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<User> optional = userService.findById(user.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(user));
        assertTrue(optional.get().getOrders().contains(order));
    }

    @Test
    void putUserOrderDenied() throws Exception {
        mockMvc
                .perform(put(USERS + "/" + 1 + "/orders/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUserOrder() throws Exception {
        Order order = populateOrder();
        User user = userService.findById(2L).get();
        user.getOrders().add(order);

        mockMvc
                .perform(delete(USERS + "/" + user.getId() + "/orders/" + order.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<User> optional = userService.findById(user.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(user));
        assertFalse(optional.get().getOrders().contains(order));
    }

    @Test
    void deleteUserOrderSameId() throws Exception {
        Order order = populateOrder();
        User user = userService.findById(2L).get();
        user.getOrders().add(order);

        mockMvc
                .perform(delete(USERS + "/" + user.getId() + "/orders/" + order.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<User> optional = userService.findById(user.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(user));
        assertFalse(optional.get().getOrders().contains(order));
    }

    @Test
    void deleteUserOrderDenied() throws Exception {
        mockMvc
                .perform(delete(USERS + "/" + 1 + "/orders/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}