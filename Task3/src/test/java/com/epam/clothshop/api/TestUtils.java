package com.epam.clothshop.api;

import com.epam.clothshop.model.*;
import com.epam.clothshop.security.Role;
import com.epam.clothshop.service.*;
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
    @Autowired
    private VendorService _vendorService;
    @Autowired
    private CategoryService _categoryService;
    @Autowired
    private UserService _userService;


    private static ObjectMapper objectMapper;
    private static MockMvc mockMvc;
    private static ProductService productService;
    private static OrderService orderService;
    private static VendorService vendorService;
    private static CategoryService categoryService;
    private static UserService userService;


    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();
        mockMvc = _mockMvc;
        productService = _productService;
        orderService = _orderService;
        vendorService = _vendorService;
        categoryService = _categoryService;
        userService = _userService;
    }

    public static String LOGIN_PATH = "/api/login";
    public static String LOGOUT_PATH = "/api/logout";
    public static String DENIED_PATH = "/api/denied";
    public static String JWT = "jwt";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String PRODUCTS = "/api/products";
    public static String ORDERS = "/api/orders";
    public static String VENDORS = "/api/vendors";
    public static String CATEGORIES = "/api/categories";
    public static String USERS = "/api/users";

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

    public static List<Vendor> populateVendors() {
        List<Product> products = populateProducts();
        List<Vendor> vendors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Vendor vendor = new Vendor();
            vendor.setName("vendor" + i);
        }
        for (int i = 0; i < vendors.size(); i++) {
            vendors.get(i).getProducts().add(products.get(i));
        }
        return vendorService.saveAll(vendors);
    }

    public static Vendor populateVendor() {
        List<Product> products = populateProducts();
        Vendor vendor = new Vendor();
        vendor.setName("vendor");
        vendor.setProducts(products);
        return vendorService.save(vendor);
    }

    public static Vendor createVendor() {
        List<Product> products = populateProducts();
        Vendor vendor = new Vendor();
        vendor.setName("vendor");
        vendor.setProducts(products);
        return vendor;
    }

    public static List<Category> populateCategories() {
        List<Product> products = populateProducts();
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Category category = new Category();
            category.setName("category" + i);
        }
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).getProducts().add(products.get(i));
        }
        return categoryService.saveAll(categories);
    }

    public static Category populateCategory() {
        List<Product> products = populateProducts();
        Category category = new Category();
        category.setName("category");
        category.setProducts(products);
        return categoryService.save(category);
    }

    public static Category createCategory() {
        List<Product> products = populateProducts();
        Category category = new Category();
        category.setName("category");
        category.setProducts(products);
        return category;
    }

    public static User createUser() {
        List<Order> orders = populateOrders();
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("newUser");
        user.setRole(Role.USER);
        user.setEmail("email");
        user.setLastName("lastname");
        user.setFirstName("firstname");
        user.setOrders(orders);
        return user;
    }
}
