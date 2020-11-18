package com.epam.clothshop.api;

import com.epam.clothshop.mapper.CategoryMapper;
import com.epam.clothshop.model.Category;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.service.CategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
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
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void getCategories() throws Exception {
        List<Category> categories = populateCategories();

        var origins = categories.stream().map(categoryMapper::mapLite).collect(Collectors.toList());

        String json = mockMvc
                .perform(get(CATEGORIES)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var results = objectMapper().readValue(json, new TypeReference<List<CategoryMapper.CategoryLite>>() {});

        assertTrue(results.containsAll(origins));
        assertTrue(origins.containsAll(results));
    }

    @Test
    public void getCategory() throws Exception {
        Category category = populateCategory();

        var origin = categoryMapper.mapFull(category);

        String json = mockMvc
                .perform(get(CATEGORIES + "/" + category.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var result = objectMapper().readValue(json, CategoryMapper.CategoryFull.class);

        assertTrue(origin.equals(result));
    }

    @Test
    public void postCategory() throws Exception {
        Category category = createCategory();

        String json = objectMapper().writeValueAsString(categoryMapper.mapToSave(category));

        MockHttpServletResponse response = mockMvc
                .perform(post(CATEGORIES)
                        .cookie(login("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        var result = objectMapper().readValue(response.getContentAsString(), CategoryMapper.CategoryFull.class);

        List<String> list = Arrays.asList(response.getHeader("Location").split("/"));
        Optional<Category> optional = categoryService.findById(Long.valueOf(list.get(list.size() - 1)));

        assertTrue(optional.isPresent());

        var origin = categoryMapper.mapFull(optional.get());

        assertTrue(origin.equals(result));
    }

    @Test
    public void postCategoryDenied() throws Exception {
        Category category = createCategory();

        String json = objectMapper().writeValueAsString(categoryMapper.mapToSave(category));

        mockMvc
                .perform(post(CATEGORIES)
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void putCategory() throws Exception {
        Category category = populateCategory();

        var categoryToSave = categoryMapper.mapToSave(category);
        categoryToSave.setName("categoryUpdated");

        String json = objectMapper().writeValueAsString(categoryToSave);

        mockMvc
                .perform(put(CATEGORIES + "/" + category.getId())
                        .cookie(login("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<Category> optional = categoryService.findById(category.getId());

        assertTrue(optional.isPresent());

        var origin = categoryMapper.mapFull(optional.get());

        assertTrue(origin.getName().equals("categoryUpdated"));
    }

    @Test
    public void putCategoryDenied() throws Exception {
        Category category = createCategory();

        var categoryToSave = categoryMapper.mapToSave(category);
        categoryToSave.setName("categoryUpdated");

        String json = objectMapper().writeValueAsString(categoryToSave);

        mockMvc
                .perform(put(CATEGORIES + "/" + 1)
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteCategory() throws Exception {
        Category category = populateCategory();

        mockMvc
                .perform(delete(CATEGORIES + "/" + category.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Category> optional = categoryService.findById(category.getId());

        assertFalse(optional.isPresent());
    }

    @Test
    public void deleteCategoryDenied() throws Exception {
        mockMvc
                .perform(delete(CATEGORIES + "/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void putCategoryProduct() throws Exception {
        Product product = populateProduct();
        Category category = populateCategory();

        mockMvc
                .perform(put(CATEGORIES + "/" + category.getId() + "/products/" + product.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<Category> optional = categoryService.findById(category.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(category));
        assertTrue(optional.get().getProducts().contains(product));
    }

    @Test
    public void putCategoryProductDenied() throws Exception {
        mockMvc
                .perform(put(CATEGORIES + "/" + 1 + "/products/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteCategoryProduct() throws Exception {
        Product product = populateProduct();
        Category category = populateCategory();
        category.getProducts().add(product);

        mockMvc
                .perform(delete(CATEGORIES + "/" + category.getId() + "/products/" + product.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<Category> optional = categoryService.findById(category.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(category));
        assertFalse(optional.get().getProducts().contains(product));
    }

    @Test
    public void deleteCategoryProductDenied() throws Exception {
        mockMvc
                .perform(delete(CATEGORIES + "/" + 1 + "/products/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}