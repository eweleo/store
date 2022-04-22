package com.example.Store.controller;

import com.example.Store.model.AddProductWithQuantity;
import com.example.Store.model.Product;
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
public class ProductController {


    private final ProductService productService;


    @GetMapping("products")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/products")
    ResponseEntity<Product> createProduct(@RequestBody @Valid AddProductWithQuantity toCreate) {
        Product product = productService.create(toCreate.getName(), toCreate.getQuantity());
        return ResponseEntity.created(URI.create("/" + product.getId())).body(product);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/products/{id}")
    ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody @Valid Product toUpdate) {
        if (!productService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        Product product = productService.update(id, toUpdate.getName());
        return ResponseEntity.ok(product);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/products/{name}")
    ResponseEntity<?> getProductByName(@PathVariable String name) {
        List<Product> list = productService.findAll();
        if (!productService.existByName(name)) {
            return ResponseEntity.notFound().build();
        }
        Optional<Product> product = productService.getByName(name);
        return ResponseEntity.ok(product);
    }

    @RequestMapping(value = "/products/{id}")
    ResponseEntity<?> removeProduct(@PathVariable int id) {
        if (!productService.existById(id))
            return ResponseEntity.notFound().build();
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}






