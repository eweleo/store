package com.example.Store.service;

import com.example.Store.repositories.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class InventoryServiceTest {

    @MockBean
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    @Test
    void existsById() {
        boolean expected1 = true;
        boolean expended2 = false;

        Mockito.when(inventoryRepository.existsById(eq(1))).thenReturn(true);
        assertEquals(expected1, inventoryRepository.existsById(1));

        Mockito.when(inventoryRepository.existsById(eq(2))).thenReturn(false);
        assertEquals(expended2, inventoryRepository.existsById(2));
    }
}