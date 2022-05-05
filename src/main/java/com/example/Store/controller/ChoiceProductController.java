package com.example.Store.controller;

import com.example.Store.model.AddPositionToOrder;
import com.example.Store.entities.ChoiceProduct;
import com.example.Store.entities.Order;
import com.example.Store.service.ChoiceProductService;
import com.example.Store.service.InventoryService;
import com.example.Store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor

@RestController
public class ChoiceProductController {

    private final ChoiceProductService choiceProductService;
    private final InventoryService inventoryService;
    private final OrderService orderService;

    @RequestMapping(value = "/choice_products", method = RequestMethod.GET)
    ResponseEntity<List<ChoiceProduct>> readAllChoice() {
        return ResponseEntity.ok(choiceProductService.findAll());
    }

    @RequestMapping(value = "choice_products", method = RequestMethod.POST)
    ResponseEntity<ChoiceProduct> createChoice(@RequestBody @Valid AddPositionToOrder addPositionToOrder) {
        System.out.println(addPositionToOrder.toString());
        System.out.println(inventoryService.findByProductId(addPositionToOrder.getProductId()));
        int quantityInventory = inventoryService.findByProductId(addPositionToOrder.getProductId()).getQuantity();

        if (quantityInventory == 0) {
            return ResponseEntity.notFound().build();
        } else if (quantityInventory < addPositionToOrder.getQuantity()) {
            addPositionToOrder.setQuantity(quantityInventory);
        }
        ChoiceProduct choiceProduct = choiceProductService.create(addPositionToOrder.getUserId(), addPositionToOrder.getProductId(), addPositionToOrder.getQuantity());
        return ResponseEntity.created(URI.create("/" + choiceProduct.getId())).body(choiceProduct);

    }

    @RequestMapping(value = "/choice_products/{id}", method = RequestMethod.DELETE)
    ResponseEntity<ChoiceProduct> deleteChoice(@PathVariable int id) {
        ChoiceProduct choiceProduct = choiceProductService.getById(id);
        Order order = orderService.getById(choiceProduct.getOrderId());
        if (!choiceProductService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else if (order.getDone()) {
            return ResponseEntity.badRequest().build();
        }
        choiceProductService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/choice_products/{id}", method = RequestMethod.PATCH)
    ResponseEntity<ChoiceProduct> updateChoice(@PathVariable int id, @RequestBody @Valid AddPositionToOrder toUpdate) {
        ChoiceProduct choiceProduct = choiceProductService.getById(id);
        Order order = orderService.getById(choiceProduct.getOrderId());
        if (!choiceProductService.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else if (order.getDone()) {
            return ResponseEntity.badRequest().build();
        } else if (toUpdate.getQuantity() == 0) {
            choiceProductService.delete(id);
            return ResponseEntity.noContent().build();
        }
        choiceProduct = choiceProductService.update(id, toUpdate.getQuantity());
        return ResponseEntity.ok(choiceProduct);
    }
}
