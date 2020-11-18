package com.epam.clothshop.api;

import com.epam.clothshop.mapper.VendorMapper;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.Vendor;
import com.epam.clothshop.service.VendorService;
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
public class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private VendorMapper vendorMapper;

    @Test
    public void getVendors() throws Exception {
        List<Vendor> vendors = populateVendors();

        var origins = vendors.stream().map(vendorMapper::mapLite).collect(Collectors.toList());

        String json = mockMvc
                .perform(get(VENDORS)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var results = objectMapper().readValue(json, new TypeReference<List<VendorMapper.VendorLite>>() {});

        assertTrue(results.containsAll(origins));
        assertTrue(origins.containsAll(results));
    }

    @Test
    public void getVendor() throws Exception {
        Vendor vendor = populateVendor();

        var origin = vendorMapper.mapFull(vendor);

        String json = mockMvc
                .perform(get(VENDORS + "/" + vendor.getId())
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var result = objectMapper().readValue(json, VendorMapper.VendorFull.class);

        assertTrue(origin.equals(result));
    }

    @Test
    public void postVendor() throws Exception {
        Vendor vendor = createVendor();

        String json = objectMapper().writeValueAsString(vendorMapper.mapToSave(vendor));

        MockHttpServletResponse response = mockMvc
                .perform(post(VENDORS)
                        .cookie(login("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        var result = objectMapper().readValue(response.getContentAsString(), VendorMapper.VendorFull.class);

        List<String> list = Arrays.asList(response.getHeader("Location").split("/"));
        Optional<Vendor> optional = vendorService.findById(Long.valueOf(list.get(list.size() - 1)));

        assertTrue(optional.isPresent());

        var origin = vendorMapper.mapFull(optional.get());

        assertTrue(origin.equals(result));
    }

    @Test
    public void postVendorDenied() throws Exception {
        Vendor vendor = createVendor();

        String json = objectMapper().writeValueAsString(vendorMapper.mapToSave(vendor));

        mockMvc
                .perform(post(VENDORS)
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void putVendor() throws Exception {
        Vendor vendor = populateVendor();

        var vendorToSave = vendorMapper.mapToSave(vendor);
        vendorToSave.setName("vendorUpdated");

        String json = objectMapper().writeValueAsString(vendorToSave);

        mockMvc
                .perform(put(VENDORS + "/" + vendor.getId())
                        .cookie(login("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<Vendor> optional = vendorService.findById(vendor.getId());

        assertTrue(optional.isPresent());

        var origin = vendorMapper.mapFull(optional.get());

        assertTrue(origin.getName().equals("vendorUpdated"));
    }

    @Test
    public void putVendorDenied() throws Exception {
        Vendor vendor = createVendor();

        var vendorToSave = vendorMapper.mapToSave(vendor);
        vendorToSave.setName("vendorUpdated");

        String json = objectMapper().writeValueAsString(vendorToSave);

        mockMvc
                .perform(put(VENDORS + "/" + 1)
                        .cookie(login("user", "user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteVendor() throws Exception {
        Vendor vendor = populateVendor();

        mockMvc
                .perform(delete(VENDORS + "/" + vendor.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Vendor> optional = vendorService.findById(vendor.getId());

        assertFalse(optional.isPresent());
    }

    @Test
    public void deleteVendorDenied() throws Exception {
        mockMvc
                .perform(delete(VENDORS + "/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void putVendorProduct() throws Exception {
        Product product = populateProduct();
        Vendor vendor = populateVendor();

        mockMvc
                .perform(put(VENDORS + "/" + vendor.getId() + "/products/" + product.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<Vendor> optional = vendorService.findById(vendor.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(vendor));
        assertTrue(optional.get().getProducts().contains(product));
    }

    @Test
    public void putVendorProductDenied() throws Exception {
        mockMvc
                .perform(put(VENDORS + "/" + 1 + "/products/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteVendorProduct() throws Exception {
        Product product = populateProduct();
        Vendor vendor = populateVendor();
        vendor.getProducts().add(product);

        mockMvc
                .perform(delete(VENDORS + "/" + vendor.getId() + "/products/" + product.getId())
                        .cookie(login("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Optional<Vendor> optional = vendorService.findById(vendor.getId());

        assertTrue(optional.isPresent());

        assertTrue(optional.get().equals(vendor));
        assertFalse(optional.get().getProducts().contains(product));
    }

    @Test
    public void deleteVendorProductDenied() throws Exception {
        mockMvc
                .perform(delete(VENDORS + "/" + 1 + "/products/" + 1)
                        .cookie(login("user", "user")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}