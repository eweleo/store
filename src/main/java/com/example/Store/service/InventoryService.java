package com.example.Store.service;

import com.example.Store.model.Inventory;
import com.example.Store.model.Product;
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
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductsRepository productsRepository;

    public Inventory update(int id, int quantity) {
        Inventory inventory = inventoryRepository.getById(id);
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);

        return inventory;
    }

    public Inventory findByProductId(int id) {
        List<Inventory> list = inventoryRepository.findAll();
        Inventory inventory = list.stream().filter(inventory1 -> inventory1.getProduct().getId() == id).findFirst().get();
        return inventory;
    }

    public boolean existsById(int id) {
        if (inventoryRepository.existsById(id))
            return true;
        return false;
    }

    public List<Inventory> findAll() {
        List<Inventory> list = new ArrayList<>();
        list.addAll(inventoryRepository.findAll());
        return list;
    }

    public boolean existByProductsName(String name) {
        List<Inventory> list = inventoryRepository.findAll();
        if (list.stream().noneMatch(inventory -> inventory.getProduct().getName().equals(name)))
            return true;
        return false;
    }

    public Optional<Inventory> getByProductsName(String name) {
        List<Inventory> list = inventoryRepository.findAll();
        return list.stream().filter(inventory1 -> inventory1.getProduct().getName().equals(name)).findFirst();
    }

    public void delete(int id) {
        Inventory inventory = inventoryRepository.getById(id);
        Product product = inventory.getProduct();
        inventoryRepository.delete(inventory);
        productsRepository.delete(product);
    }

}
