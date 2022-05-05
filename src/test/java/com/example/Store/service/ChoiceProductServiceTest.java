package com.example.Store.service;

import com.example.Store.entities.ChoiceProduct;
import com.example.Store.entities.Order;
import com.example.Store.entities.Product;
import com.example.Store.entities.User;
import com.example.Store.repositories.ChoiceProductRepository;
import com.example.Store.repositories.ProductsRepository;
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
class ChoiceProductServiceTest {

    @MockBean
    private ChoiceProductRepository choiceProductRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductsRepository productsRepository;

    @Autowired
    private ChoiceProductService choiceProductService;


    @Test
    void create() {

        Product product = new Product();
        product.setName("salt");
        product.setId(1);

        User user = new User();
        user.setName("Name");
        user.setSurname("Surname");
        user.setId(1);

        Order order = new Order(user);
        order.setId(1);

        List orders = new ArrayList<>();
        orders.add(order);

        user.setOrders(orders);

        ChoiceProduct choiceProduct = new ChoiceProduct(product, order, 1);

        order.setChoiceProduct(choiceProduct);

        Mockito.when(userRepository.existsById(anyInt())).thenReturn(true);
        Mockito.when(productsRepository.getById(anyInt())).thenReturn(product);
        Mockito.when(userRepository.getById(anyInt())).thenReturn(user);

        ChoiceProduct choiceProduct1 = choiceProductService.create(1, 1, 1);

        assertEquals(choiceProduct, choiceProduct1);


    }

    @Test
    void existById() {
        boolean expected1 = true;
        boolean expended2 = false;

        Mockito.when(choiceProductRepository.existsById(eq(1))).thenReturn(true);
        assertEquals(expected1, choiceProductService.existsById(1));

        Mockito.when((choiceProductRepository.existsById(eq(2)))).thenReturn(false);
        assertEquals(expended2, choiceProductService.existsById(2));
    }
}