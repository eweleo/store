package com.example.Store.service;

import com.example.Store.entities.Inventory;
import com.example.Store.entities.Product;
import com.example.Store.repositories.InventoryRepository;
import com.example.Store.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Transactional
@Service
public class ProductService {

    private final ProductsRepository productsRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryService inventoryService;


    public Product create(String name, int quantity) {

        Product product = new Product(name);
        Inventory inventory = new Inventory(product, quantity);
        inventoryRepository.save(inventory);
        productsRepository.save(product);
        return product;
    }

    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        list.addAll(productsRepository.findAll());
        return list;
    }

    public Product update(int id, String name) {
        Product product = productsRepository.getById(id);
        product.setName(name);
        productsRepository.save(product);
        return product;
    }

    public boolean existById(int id) {
        if (productsRepository.existsById(id))
            return true;
        return false;
    }

    public boolean existByName(String name) {
        List<Product> list = productsRepository.findAll();
        if (list.stream().noneMatch(product -> product.getName().equals(name)))
            return false;
        return true;
    }

    public Optional<Product> getByName(String name) {
        List<Product> list = productsRepository.findAll();
        return list.stream().filter(product1 -> product1.getName().equals(name)).findFirst();
    }

    public void delete(int id) {
        Product product = productsRepository.getById(id);
        Inventory inventory = inventoryService.findByProductId(id);
        productsRepository.delete(product);
        inventoryRepository.delete(inventory);
    }

}
