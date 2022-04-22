package com.example.Store.service;


import com.example.Store.model.Order;
import com.example.Store.model.User;
import com.example.Store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;

    public User create(String name, String surname) {
        User user = new User(name, surname);

        return userRepository.save(user);
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        list.addAll(userRepository.findAll());
        return list;
    }

    public User update(int id, String name, String surname) {
        User user = userRepository.getById(id);
        user.setName(name);
        user.setSurname(surname);

        return userRepository.save(user);
    }

    public boolean existsById(int id) {
        if (userRepository.existsById(id))
            return true;
        return false;
    }

    public void delete(int id) {
        User user = userRepository.getById(id);
        List<Order> orders = user.getOrders();
        if (orders.stream().anyMatch(order -> order.getDone() == false)) {
            Optional<Order> order = orders.stream().filter(order1 -> order1.getDone() == false).findFirst();
            orderService.delete(order.get().getId());
        }
        userRepository.delete(user);
    }
}


