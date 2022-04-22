package com.example.Store.service;

import com.example.Store.model.Order;
import com.example.Store.model.User;
import com.example.Store.repositories.OrderRepository;
import com.example.Store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Test
    void create() {
        User user = new User("Name", "Surname");
        user.setId(1);
        Order order1 = new Order(user);
        order1.setId(1);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        user.setOrders(orderList);

        Mockito.when(userRepository.existsById(anyInt())).thenReturn(true);
        Mockito.when(userRepository.getById(anyInt())).thenReturn(user);

        assertEquals(order1, orderService.create(1));

    }

    @Test
    void updateToDone() {
        User user = new User("name", "surname");
        user.setId(1);
        Order trueOrder = new Order(user);
        trueOrder.setDone(true);
        Order falseOrder = new Order(user);
        List<Order> orderList = new ArrayList<>();
        orderList.add(trueOrder);
        orderList.add(falseOrder);
        user.setOrders(orderList);

        Mockito.when(orderRepository.getById(anyInt())).thenReturn(falseOrder);

        assertEquals(trueOrder.getDone(), orderService.updateToDone(falseOrder.getId()).getDone());
    }

    @Test
    void existsById() {
        boolean expended1 = true;
        boolean expended2 = false;

        Mockito.when(orderRepository.existsById(eq(1))).thenReturn(true);
        assertEquals(expended1, orderService.existsById(1));

        Mockito.when(orderRepository.existsById(eq(2))).thenReturn(false);
        assertEquals(expended2, orderService.existsById(2));


    }
}