package com.example.Store.controller;

import com.example.Store.model.AddProductWithQuantity;
import com.example.Store.entities.Inventory;
import com.example.Store.entities.Product;
import com.example.Store.service.InventoryService;
import com.example.Store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@RestController
public class InventoryController {


    private final InventoryService inventoryService;
    private final ProductService productService;


    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> getAll() {
        return new ResponseEntity<>(inventoryService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/inventory/{id}")
    ResponseEntity<Inventory> updateInventory(@PathVariable int id, @RequestBody @Valid Inventory toUpdate) {
        if (!inventoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Inventory inventory = inventoryService.update(id, toUpdate.getQuantity());
        return ResponseEntity.ok(inventory);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/inventory/{name}")
    ResponseEntity<?> getInventoryByProductsName(@PathVariable String name) {
        List<Inventory> list = inventoryService.findAll();
        if (!inventoryService.existByProductsName(name)) {
            return ResponseEntity.notFound().build();
        }
        Optional<Inventory> inventory = inventoryService.getByProductsName(name);
        return ResponseEntity.ok(inventory);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/inventory/{id}")
    ResponseEntity<?> removeInventory(@PathVariable int id) {
        if (!inventoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @RequestMapping(method = RequestMethod.POST, value = "/inventory")
    ResponseEntity<Inventory> createInventory(@RequestBody @Valid AddProductWithQuantity addProductWithQuantity){
        Product product = productService.create(addProductWithQuantity.getName(), addProductWithQuantity.getQuantity());
        Inventory inventory = inventoryService.findByProductId(product.getId());
        return ResponseEntity.created(URI.create("/" + inventory.getId())).body(inventory);
    }

}
