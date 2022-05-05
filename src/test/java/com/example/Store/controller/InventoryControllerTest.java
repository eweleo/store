package com.example.Store.controller;

import com.example.Store.entities.Inventory;
import com.example.Store.entities.Product;
import com.example.Store.service.InventoryService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private InventoryService inventoryService;

    @Before
    List<Inventory> createList() {
        List list = new ArrayList<>();
        list.add(new Inventory(new Product("product1")));
        list.add((new Inventory(new Product("product2"))));
        return list;
    }

    @Test
    void getAll() throws Exception {

        List list = createList();
        String responseJson = objectMapper.writeValueAsString(list);

        Mockito.when(inventoryService.findAll()).thenReturn(list);

        MvcResult result = mvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(responseJson, result.getResponse().getContentAsString());
    }


    @Test
    void updateInventory() throws Exception {
        int id = 1;
        int quantity = 100;
        Inventory inventory = createList().get(id);
        String json = "{\"quantity\" : " + quantity + "}";
        inventory.setQuantity(quantity);
        String responseJson = objectMapper.writeValueAsString(inventory);

        Mockito.when(inventoryService.existsById(eq(id))).thenReturn(true);
        Mockito.when(inventoryService.update(eq(id), eq(quantity))).thenReturn(inventory);

        MvcResult result = mvc.perform(patch("/inventory/" + id).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());

    }

    @Test
    void getInventoryByProductsName() throws Exception {
        Inventory inventory = createList().get(0);
        String name = inventory.getProduct().getName();
        String responseJson = objectMapper.writeValueAsString(name);

        Mockito.when(inventoryService.existByProductsName(eq(name))).thenReturn(true);
        Mockito.when(inventoryService.getByProductsName(eq(name))).thenReturn(Optional.of(inventory));

        MvcResult result = mvc.perform(get("/inventory/" + name))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(responseJson, result.getResponse().getContentAsString());

    }
}