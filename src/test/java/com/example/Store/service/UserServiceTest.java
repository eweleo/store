package com.example.Store.service;

import com.example.Store.repositories.UserRepository;
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
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void existsById() {
        boolean expected1 = true;
        boolean expected2 = false;

        Mockito.when(userRepository.existsById(eq(1))).thenReturn(true);
        Mockito.when(userRepository.existsById(eq(2))).thenReturn(false);

        assertEquals(expected1, userService.existsById(1));
        assertEquals(expected2, userService.existsById(2));
    }
}