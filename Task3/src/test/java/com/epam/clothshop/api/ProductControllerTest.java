package com.epam.clothshop.api;

import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.model.Product;
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
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @Test
    void getProducts() throws Exception {
        List<Product> products = populateProducts();

        var origins = products.stream().map(productMapper::mapLite).collect(Collectors.toList());

        String json = mockMvc
                .perform(get(PRODUCTS)
                .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var results = objectMapper().readValue(json, new TypeReference<List<ProductMapper.ProductLite>>() {});

        assertTrue(origins.containsAll(results));
        assertTrue(results.containsAll(origins));
    }

    @Test
    void getProduct() throws Exception {
        Product product = populateProduct();

        var origin = productMapper.mapFull(product);

        String json = mockMvc
                .perform(get(PRODUCTS + "/" + product.getId())
                .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var result = objectMapper().readValue(json, ProductMapper.ProductFull.class);

        assertTrue(result.equals(origin));
    }

    @Test
    void postProduct() throws Exception {
        Product product = createProduct();

        String json = objectMapper().writeValueAsString(productMapper.mapToSave(product));

        MockHttpServletResponse response = mockMvc
                .perform(post(PRODUCTS)
                        .cookie(login("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        var result = objectMapper().readValue(response.getContentAsString(), ProductMapper.ProductFull.class);

        List<String> list = Arrays.asList(response.getHeader("Location").split("/"));
        Optional<Product> optional = productService.findById(Long.valueOf(list.get(list.size() - 1)));

        assertTrue(optional.isPresent());

        var origin = productMapper.mapFull(optional.get());

        assertTrue(origin.equals(result));
    }

    @Test
    void postProductDenied() throws Exception {
        Product product = createProduct();

        var productToSave = productMapper.mapToSave(product);

        String json = objectMapper().writeValueAsString(productToSave);

        mockMvc
                .perform(post(PRODUCTS)
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void putProduct() throws Exception{
        Product product = populateProduct();

        var productToSave = productMapper.mapToSave(product);
        productToSave.setName("productUpdated");

        String json = objectMapper().writeValueAsString(productToSave);

        mockMvc
                .perform(put(PRODUCTS + "/" + product.getId())
                        .cookie(login("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Product> optional = productService.findById(product.getId());

        assertTrue(optional.isPresent());
        assertTrue(optional.get().getName().equals("productUpdated"));
    }

    @Test
    void putProductDenied() throws Exception{
        Product product = createProduct();

        var productToSave = productMapper.mapToSave(product);

        String json = objectMapper().writeValueAsString(productToSave);

        mockMvc
                .perform(put(PRODUCTS + "/" + 1)
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteProduct() throws Exception {
        Product product = populateProduct();

        mockMvc
                .perform(delete(PRODUCTS + "/" + product.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Product> optional = productService.findById(product.getId());

        assertFalse(optional.isPresent());
    }

    @Test
    void deleteProductDenied() throws Exception {
        mockMvc
                .perform(delete(PRODUCTS + "/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}