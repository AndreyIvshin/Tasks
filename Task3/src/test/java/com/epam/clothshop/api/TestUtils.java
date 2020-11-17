package com.epam.clothshop.api;

import com.epam.clothshop.model.Order;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.OrderService;
import com.epam.clothshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {

    @Autowired
    private MockMvc _mockMvc;
    @Autowired
    private ProductService _productService;
    @Autowired
    private OrderService _orderService;


    private static ObjectMapper objectMapper;
    private static MockMvc mockMvc;
    private static ProductService productService;
    private static OrderService orderService;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();
        mockMvc = _mockMvc;
        productService = _productService;
        orderService = _orderService;
    }

    public static String LOGIN_PATH = "/api/login";
    public static String LOGOUT_PATH = "/api/logout";
    public static String DENIED_PATH = "/api/denied";
    public static String JWT = "jwt";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String PRODUCTS = "/api/products";
    public static String ORDERS = "/api/orders";

    public static Cookie login(String username, String password) throws Exception {
        return mockMvc
                .perform(post(LOGIN_PATH)
                        .param(USERNAME, username)
                        .param(PASSWORD, password))
                .andReturn().getResponse().getCookie(JWT);
    }

    public static ObjectMapper objectMapper() {
        return objectMapper;
    }

    public static List<Product> populateProducts() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName("product" + i);
            product.setPrice(i * 10);
            product.setQuantity(i * 100);
            products.add(product);
        }
        return productService.saveAll(products);
    }

    public static Product populateProduct() {
        Product product = new Product();
        product.setName("product");
        product.setPrice(10);
        product.setQuantity(100);
        return productService.save(product);
    }

    public static Product createProduct() {
        Product product = new Product();
        product.setName("product");
        product.setPrice(10);
        product.setQuantity(100);
        return product;
    }

    public static List<Order> populateOrders() {
        List<Product> products = populateProducts();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setComplete(true);
            order.setCreatedAt(Date.from(Instant.now()));
            order.setShipDate(Date.from(Instant.now()));
            order.setStatus(Order.Status.DELIVERED);
            orders.add(order);
        }
        orderService.saveAll(orders);
        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).getProducts().add(products.get(i));
        }
        return orderService.saveAll(orders);
    }

    public static Order populateOrder() {
        List<Product> products = populateProducts();
        Order order = new Order();
        order.setComplete(true);
        order.setCreatedAt(Date.from(Instant.now()));
        order.setShipDate(Date.from(Instant.now()));
        order.setStatus(Order.Status.DELIVERED);
        order.setProducts(products);
        return orderService.save(order);
    }

    public static Order createOrder() {
        List<Product> products = populateProducts();
        Order order = new Order();
        order.setComplete(true);
        order.setCreatedAt(Date.from(Instant.now()));
        order.setShipDate(Date.from(Instant.now()));
        order.setStatus(Order.Status.DELIVERED);
        order.setProducts(products);
        return order;
    }
}
