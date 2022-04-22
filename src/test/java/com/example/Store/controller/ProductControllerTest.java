package com.example.Store.controller;

import com.example.Store.model.Product;
import com.example.Store.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Before
    List<Product> createList() {
        List list = new ArrayList<>();
        list.add(new Product("product1"));
        list.add(new Product("product2"));
        list.add(new Product("product3"));
        return list;
    }

    @Test
    void getAll() throws Exception {

        String responseJson = objectMapper.writeValueAsString(createList());

        Mockito.when(productService.findAll()).thenReturn(createList());

        MvcResult result = mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(responseJson, result.getResponse().getContentAsString());
    }

    @Test
    void createProduct() throws Exception {

        String name = "sok";
        Product product = new Product(name);
        product.setId(1);

        String json = "{\"name\" : \"" + name + "\"}";

        String responseJson = objectMapper.writeValueAsString(product);

        Mockito.when(productService.create(eq(name), anyInt())).thenReturn(product);

        MvcResult result = mvc.perform(post("/products").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertEquals(responseJson, result.getResponse().getContentAsString());
    }

    @Test
    void updateProduct() throws Exception {

        int id = 1;
        Product product = createList().get(id);

        Mockito.when(productService.existById(eq(id))).thenReturn(true);

        product.setName("kot");
        String json = "{\"name\":\"kot\"}";
        String responseJson = objectMapper.writeValueAsString(product);

        Mockito.when(productService.update(eq(id), eq("kot"))).thenReturn(product);


        MvcResult result = mvc.perform(patch("/products/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());


    }

    @Test
    void getProductByName() throws Exception {

        String name = "product1";
        Product product = createList().get(1);
        String responseJson = objectMapper.writeValueAsString(product);

        Mockito.when(productService.existByName(eq(name))).thenReturn(true);

        Mockito.when(productService.getByName(eq(name))).thenReturn(java.util.Optional.ofNullable(product));

        MvcResult result = mvc.perform(get("/products/" + name))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());
    }
}