package com.example.Store.service;

import com.example.Store.model.Order;
import com.example.Store.model.User;
import com.example.Store.repositories.OrderRepository;
import com.example.Store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        list.addAll(orderRepository.findAll());
        return list;
    }

    public Order create(int userId) {
        Order order = new Order(false);
        if (!userRepository.existsById(userId)) {
            return null;
        }
        User user = userRepository.getById(userId);


        if (userRepository.getById(userId).getOrders().stream().anyMatch(order1 -> order1.getDone() == false)) {
            return userRepository.getById(userId).getOrders().stream().findAny().get();
        }
        order.setUser(user);
        return orderRepository.save(order);
    }

    public Order updateToDone(int id) {
        Order order = orderRepository.getById(id);
        if (order.getDone()) {
            return order;
        }
        order.setDone(true);
        orderRepository.save(order);
        return order;
    }

    public List<Order> allTrue() {
        List<Order> list = new ArrayList<>();
        list.addAll(orderRepository.findAll());
        return list.stream().filter(order -> order.getDone() == true).collect(Collectors.toList());
    }

    public List<Order> allFalse() {
        List<Order> list = new ArrayList<>();
        list.addAll(orderRepository.findAll());
        return list.stream().filter(order -> order.getDone() == false).collect(Collectors.toList());
    }

    public boolean existsById(int id) {
        if (orderRepository.existsById(id)) {
            return true;
        }
        return false;
    }

    public Order getById(int id) {
        return orderRepository.getById(id);
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    public void delete(int id) {
        Order order = orderRepository.getById(id);
        orderRepository.delete(order);
    }

}
