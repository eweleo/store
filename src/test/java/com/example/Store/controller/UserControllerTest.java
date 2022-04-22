package com.example.Store.controller;

import com.example.Store.model.User;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Before
    List<User> createList() {
        List<User> list = new ArrayList<>();
        list.add(new User("User1", "User1"));
        list.add(new User("User2", "User2"));
        list.add(new User("User3", "User3"));
        return list;

    }

    @Test
    void getAll() throws Exception {

        String responseJson = objectMapper.writeValueAsString(createList());
        Mockito.when(userService.findAll()).thenReturn(createList());


        MvcResult result = mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());
    }

    @Test
    void createUser() throws Exception {

        String json = "{\"name\": \"Ala\",\"surname\": \"Kot\"}";
        User user = new User("Ala", "Kot");
        user.setId(1);
        String responseJson = objectMapper.writeValueAsString(user);

        Mockito.when(userService.create(eq("Ala"), eq("Kot"))).thenReturn(user);

        MvcResult result = mvc.perform(post("/users").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals(responseJson, result.getResponse().getContentAsString());


    }

    @Test
    void updateUser() throws Exception {

        int id = 1;
        User user = createList().get(id);
        String name = "ala";
        String surname = "kot";
        String json = "{\"name\" : \"" + name + "\", \"surname\" : \"" + surname + "\"}";


        Mockito.when(userService.existsById(eq(id))).thenReturn(true);

        user.setName(name);
        user.setSurname(surname);

        String responseJson = objectMapper.writeValueAsString(user);

        Mockito.when(userService.update(eq(id), eq(name), anyString())).thenReturn(user);

        MvcResult result = mvc.perform(patch("/users/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());
    }
}