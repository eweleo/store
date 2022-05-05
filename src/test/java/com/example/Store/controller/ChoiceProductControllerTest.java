package com.example.Store.controller;

import com.example.Store.entities.ChoiceProduct;
import com.example.Store.entities.Order;
import com.example.Store.entities.Product;
import com.example.Store.entities.User;
import com.example.Store.service.ChoiceProductService;
import com.example.Store.service.OrderService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ChoiceProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ChoiceProductService choiceProductService;

    @MockBean
    private OrderService orderService;

    @Before
    private List<Order> createOrderList() {

        Order order1 = new Order(new User("a", "b"));
        Order order2 = new Order(new User("2", "2"));
        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);

        return orderList;
    }

    @Before
    private List<ChoiceProduct> creteList() {
        Order order1 = createOrderList().stream().findFirst().get();
        Order order2 = createOrderList().get(order1.getId() + 1);

        Product product1 = new Product("product1");
        Product product2 = new Product("product2");

        ChoiceProduct chP1 = new ChoiceProduct(product1, order1, 1);
        ChoiceProduct chP2 = new ChoiceProduct(product2, order1, 5);
        ChoiceProduct chP3 = new ChoiceProduct(product1, order2, 7);
        ChoiceProduct chP4 = new ChoiceProduct(product2, order2, 2);
        order1.setChoiceProduct(chP1);
        order1.setChoiceProduct(chP2);
        order2.setChoiceProduct(chP3);
        order2.setChoiceProduct(chP4);
        List<ChoiceProduct> list = new ArrayList<>();
        list.add(chP1);
        list.add(chP2);
        list.add(chP3);
        list.add(chP4);

        return list;
    }

    @Test
    void readAllChoice() throws Exception {
        List<ChoiceProduct> list = creteList();
        String responseJson = objectMapper.writeValueAsString(list);

        Mockito.when(choiceProductService.findAll()).thenReturn(list);

        MvcResult result = mvc.perform(get("/choice_products"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());

    }

    @Test
    void createChoice() throws Exception {
        String json = "{\"userId\" : 1, \"productId\" : 1, \"quantity\" : 1}";

        ChoiceProduct choiceProduct = creteList().stream().findFirst().get();
        String responseJson = objectMapper.writeValueAsString(choiceProduct);

        Mockito.when(choiceProductService.create(eq(1), eq(1), eq(1))).thenReturn(choiceProduct);

        MvcResult result = mvc.perform(post("/choice_products").content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(responseJson,
                result.getResponse().getContentAsString());
    }

    @Test
    void deleteChoice() throws Exception {
        int id = 1;
        User user = new User("name", "surname");
        user.setId(id);
        Order order = new Order(user);
        order.setId(id);
        Product product = new Product("apple");
        product.setId(id);
        ChoiceProduct choiceProduct = new ChoiceProduct(product, order, 1);
        choiceProduct.setId(id);
        List<ChoiceProduct> choiceProductList = new ArrayList<>();
        choiceProductList.add(choiceProduct);
        order.setChoiceProducts(choiceProductList);

        Mockito.when(choiceProductService.existsById(eq(id))).thenReturn(true);
        Mockito.when(choiceProductService.getById(anyInt())).thenReturn(choiceProduct);
        Mockito.when(orderService.getById(anyInt())).thenReturn(order);


        this.mvc.perform(delete("/choice_products/" + id))
                .andExpect(status().isNoContent());

    }

    @Test
    void updateChoice() throws Exception {
        int id = 2;
        int quantity = 10;
        String json = "{\"quantity\" : " + quantity + "}";
        ChoiceProduct choiceProduct = creteList().get(id);

        Order order = createOrderList().get(1);
        order.setDone(false);
        order.setId(1);
        order.setChoiceProduct(choiceProduct);
        choiceProduct.setOrder(order);

        choiceProduct.setQuantity(choiceProduct.getQuantity() + quantity);
        String responseJson = objectMapper.writeValueAsString(choiceProduct);

        Mockito.when(orderService.existsById(anyInt())).thenReturn(true);
        Mockito.when(choiceProductService.existsById(eq(id))).thenReturn(true);
        Mockito.when(orderService.findById(anyInt())).thenReturn(Optional.of(order));
        Mockito.when(orderService.getById(anyInt())).thenReturn(order);


        Mockito.when(choiceProductService.getById(eq(id))).thenReturn(choiceProduct);
        Mockito.when(choiceProductService.update(eq(id), eq(quantity))).thenReturn(choiceProduct);

        MvcResult result = mvc.perform(patch("/choice_products/" + id)
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());
    }
}