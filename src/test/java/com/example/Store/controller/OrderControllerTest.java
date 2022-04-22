package com.example.Store.controller;

import com.example.Store.model.Order;
import com.example.Store.model.User;
import com.example.Store.service.OrderService;
import com.example.Store.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;


    @Before
    List<Order> createList() {

        User user1 = new User("User1", "User1");
        user1.setId(1);
        User user2 = new User("User2", "User2");
        user2.setId(2);
        User user3 = new User("User3", "User3");
        user3.setId(3);
        List list = new ArrayList<>();
        list.add(new Order(user1));
        list.add(new Order(user2));
        list.add(new Order(user3, true));
        return list;

    }

    @Test
    void readAll() throws Exception {
        List list = createList();

        String responseJson = objectMapper.writeValueAsString(list);

        Mockito.when(orderService.findAll()).thenReturn(list);

        MvcResult result = mvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());
    }

    @Test
    void readOrder() throws Exception {
        int id = 1;
        Order order = createList().get(id);
        String responseJson = objectMapper.writeValueAsString(order);

        Mockito.when(orderService.existsById(eq(id))).thenReturn(true);
        Mockito.when(orderService.getById(eq(id))).thenReturn(order);

        MvcResult result = mvc.perform(get("/orders/" + id))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());
    }

    @Test
    void readDoneIsTrue() throws Exception {
        List<Order> list = createList().stream().filter(order -> order.getDone() == true).collect(Collectors.toList());
        String responseJson = objectMapper.writeValueAsString(list);

        Mockito.when(orderService.allTrue()).thenReturn(list);

        MvcResult result = mvc.perform(get("/orders/true"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());

    }

    @Test
    void readDoneIsFalse() throws Exception {
        List<Order> list = createList().stream().filter(order -> order.getDone() == false).collect(Collectors.toList());
        String responseJson = objectMapper.writeValueAsString(list);

        Mockito.when(orderService.allFalse()).thenReturn(list);

        MvcResult result = mvc.perform(get("/orders/false"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());

    }

    @Test
    void updateOrder() throws Exception {
        int id = 1;
        Order order = createList().get(id);
        order.setDone(true);
        String responseJson = objectMapper.writeValueAsString(order);

        Mockito.when(orderService.existsById(eq(id))).thenReturn(true);
        Mockito.when(orderService.updateToDone(eq(id))).thenReturn(order);

        MvcResult result = mvc.perform(patch("/orders/" + id))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());

    }
}