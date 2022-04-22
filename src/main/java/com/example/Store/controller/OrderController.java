package com.example.Store.controller;


import com.example.Store.model.Order;
import com.example.Store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
public class OrderController {

    final List list;


    private final OrderService orderService;

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


}
