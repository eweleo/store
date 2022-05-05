package com.example.Store.controller;


import com.example.Store.entities.Order;
import com.example.Store.entities.User;
import com.example.Store.service.OrderService;
import com.example.Store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor

@RestController
public class OrderController {

    final List list;
    private final OrderService orderService;
    private final UserService userService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    ResponseEntity<List<Order>> readAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @RequestMapping(value = "orders/{id}", method = RequestMethod.GET)
    ResponseEntity<?> readOrder(@PathVariable int id) {
        if (!orderService.existsById(id))
            return ResponseEntity.notFound().build();
        Order order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @RequestMapping(value = "orders/true", method = RequestMethod.GET)
    ResponseEntity<List<Order>> readDoneIsTrue() {
        return ResponseEntity.ok(orderService.allTrue());
    }

    @RequestMapping(value = "orders/false", method = RequestMethod.GET)
    ResponseEntity<List<Order>> readDoneIsFalse() {
        return ResponseEntity.ok(orderService.allFalse());
    }


    @RequestMapping(value = "/orders/{id}", method = RequestMethod.PATCH)
    ResponseEntity<Order> updateOrder(@PathVariable int id) {
        if (!orderService.existsById(id))
            return ResponseEntity.notFound().build();
        Order order = orderService.updateToDone(id);
        return ResponseEntity.ok(order);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> removeOrder(@PathVariable int id) {
        if (!orderService.existsById(id))
            return ResponseEntity.notFound().build();
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orders")
    ResponseEntity<Order> createOrder(@RequestBody User user){
        System.out.println("\n" + user.getId());
        if(!userService.existsById(user.getId())){
            return ResponseEntity.notFound().build();
        }
        Order order = orderService.create(user.getId());
        return ResponseEntity.created(URI.create("/" + order.getId())).body(order);
    }


}
