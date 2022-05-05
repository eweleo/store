package com.example.Store.controller;

import com.example.Store.entities.User;
import com.example.Store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor

@RestController
public class UserController {

    private final UserService userService;


    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/users")
    ResponseEntity<User> createUser(@RequestBody @Valid User toCreate) {
        User user = userService.create(toCreate.getName(), toCreate.getSurname());
        return ResponseEntity.created(URI.create("/" + user.getId())).body(user);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/users/{id}")
    ResponseEntity<User> updateUser(@PathVariable int id,@Valid @RequestBody User toUpdate) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.update(id, toUpdate.getName(), toUpdate.getSurname());
        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    ResponseEntity<?> removeUser(@PathVariable int id) {
        if (!userService.existsById(id))
            return ResponseEntity.notFound().build();
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


