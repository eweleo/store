package com.example.Store.repositories;

import com.example.Store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Product, Integer> {

}
