package com.example.Store.service;

import com.example.Store.model.Product;
import com.example.Store.repositories.ProductsRepository;
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
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceTest {

    @MockBean
    private ProductsRepository productsRepository;

    @Autowired
    private ProductService productService;


    @Test
    void existById() {
        boolean expended1 = true;
        boolean expended2 = false;

        Mockito.when(productsRepository.existsById(eq(1))).thenReturn(true);
        assertEquals(expended1, productService.existById(1));

        Mockito.when(productsRepository.existsById(eq(2))).thenReturn(false);
        assertEquals(expended2, productService.existById(2));
    }

    @Test
    void existByName() {
        boolean expended = true;
        boolean expended2 = false;

        List<Product> products = new ArrayList<>();
        products.add(new Product("apple"));

        Mockito.when(productsRepository.findAll()).thenReturn(products);

        assertEquals(expended, productService.existByName("apple"));
        assertEquals(expended2, productService.existByName("salt"));

    }

}