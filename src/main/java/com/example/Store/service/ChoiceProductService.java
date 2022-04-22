package com.example.Store.service;

import com.example.Store.model.*;
import com.example.Store.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Transactional
@Service
public class ChoiceProductService {

    private final ChoiceProductRepository choiceProductRepository;
    private final ProductsRepository productsRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryService inventoryService;


    public List<ChoiceProduct> findAll() {
        List<ChoiceProduct> list = new ArrayList<>();
        list.addAll(choiceProductRepository.findAll());
        return list;
    }

    public ChoiceProduct create(int userId, int productId, int quantity) {
        if (!userRepository.existsById(userId)) {
            return null;
        }
        ChoiceProduct choiceProduct = new ChoiceProduct();
        User user = userRepository.getById(userId);
        Order order = user.getOrders().stream().filter(ord -> ord.getDone() == false)
                .findFirst().orElse(orderService.create(userId));
        Product product = productsRepository.getById(productId);
        Inventory inventory = inventoryService.findByProductId(product.getId());

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);

        if (order.getChoiceProducts().stream().
                filter(choiceProduct1 -> choiceProduct1.getProductId() == product.getId())
                .findFirst().isPresent()) {
            choiceProduct = order.getChoiceProducts().stream()
                    .filter(choiceProduct1 -> choiceProduct1.getProductId() == product.getId()).
                    findFirst().get();
            choiceProduct.setQuantity(choiceProduct.getQuantity() + quantity);
            return choiceProduct;
        }

        choiceProduct.setOrder(order);
        choiceProduct.setProduct(product);
        choiceProduct.setQuantity(quantity);
        choiceProductRepository.save(choiceProduct);
        order.setChoiceProduct(choiceProduct);
        orderRepository.save(order);


        return choiceProduct;
    }

    public void delete(int id) {
        ChoiceProduct choiceProduct = choiceProductRepository.getById(id);
        int quantity = choiceProduct.getQuantity();
        Inventory inventory = inventoryService.findByProductId(choiceProduct.getProductId());
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);
        Order order = orderRepository.getById(choiceProduct.getOrderId());
        choiceProductRepository.delete(choiceProduct);
        List list = order.getChoiceProducts();
        list.remove(choiceProduct);
        order.setChoiceProducts(list);
    }

    public ChoiceProduct update(int id, int quantity) {
        ChoiceProduct choiceProduct = choiceProductRepository.getById(id);
        int tmpQuantity = quantity - choiceProduct.getQuantity();
        Inventory inventory = inventoryService.findByProductId(choiceProduct.getProductId());
        inventory.setQuantity(inventory.getQuantity() - tmpQuantity);
        inventoryRepository.save(inventory);
        choiceProduct.setQuantity(quantity);
        return choiceProductRepository.save(choiceProduct);
    }

    public boolean existsById(int id) {
        if (choiceProductRepository.existsById(id))
            return true;
        return false;
    }

    public ChoiceProduct getById(int id) {
        return choiceProductRepository.getById(id);
    }
}
