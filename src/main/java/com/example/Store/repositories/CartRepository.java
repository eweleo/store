package com.example.Store.repositories;

import com.example.Store.entities.ChoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<ChoiceProduct, Integer> {
}
