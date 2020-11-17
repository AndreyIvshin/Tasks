package com.epam.clothshop.api;

import com.epam.clothshop.mapper.OrderMapper;
import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.OrderService;
import com.epam.clothshop.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static java.lang.Thread.sleep;
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
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @Test
    void getOrders() throws Exception {
        List<Order> orders = populateOrders();

        var origins = orders.stream().map(orderMapper::mapLite).collect(Collectors.toList());

        String json = mockMvc
                .perform(get(ORDERS)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var results = objectMapper().readValue(json, new TypeReference<List<OrderMapper.OrderLite>>() {
        });

        assertTrue(results.containsAll(origins));
        assertTrue(origins.containsAll(results));
    }

    @Test
    void getOrder() throws Exception {
        Order order = populateOrder();

        var origin = orderMapper.mapFull(order);

        String json = mockMvc
                .perform(get(ORDERS + "/" + order.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var result = objectMapper().readValue(json, OrderMapper.OrderFull.class);

        assertTrue(origin.equals(result));
    }

    @Test
    void postOrders() throws Exception {
        Order order = createOrder();

        String json = objectMapper().writeValueAsString(orderMapper.mapToSave(order));

        MockHttpServletResponse response = mockMvc
                .perform(post(ORDERS)
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        var result = objectMapper().readValue(response.getContentAsString(), OrderMapper.OrderFull.class);

        List<String> list = Arrays.asList(response.getHeader("Location").split("/"));
        Optional<Order> optional = orderService.findById(Long.valueOf(list.get(list.size() - 1)));

        assertTrue(optional.isPresent());

        var origin = orderMapper.mapFull(optional.get());

        assertTrue(origin.equals(result));
    }

    @Test
    void putOrders() throws Exception {
        Order order = populateOrder();

        var orderToSave = orderMapper.mapToSave(order);
        orderToSave.setComplete(false);

        String json = objectMapper().writeValueAsString(orderToSave);

        mockMvc
                .perform(put(ORDERS + "/" + order.getId())
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<Order> optional = orderService.findById(order.getId());

        assertTrue(optional.isPresent());

        var origin = orderMapper.mapFull(optional.get());

        assertTrue(origin.getComplete().equals(false));
    }

    @Test
    void deleteOrders() throws Exception {
        Order order = populateOrder();

        mockMvc
                .perform(delete(ORDERS + "/" + order.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Order> optional = orderService.findById(order.getId());

        assertFalse(optional.isPresent());
    }

    @Test
    void putOrderProduct() throws Exception {
        Product product = populateProduct();
        Order order = populateOrder();

        mockMvc
                .perform(put(ORDERS + "/" + order.getId() + "/products/" + product.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<Order> optional = orderService.findById(order.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(order));
        assertTrue(optional.get().getProducts().contains(product));
    }

    @Test
    void deleteOrderProduct() throws Exception {
        Product product = populateProduct();
        Order order = populateOrder();
        order.getProducts().add(product);

        mockMvc
                .perform(delete(ORDERS + "/" + order.getId() + "/products/" + product.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<Order> optional = orderService.findById(order.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(order));
        assertFalse(optional.get().getProducts().contains(product));
    }
}