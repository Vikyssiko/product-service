package com.example.test_task.repositories;

import com.example.test_task.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findTopByOrderByRatingDesc();
    Optional<Product> findTopByOrderByPriceDesc();
    Optional<Product> findTopByOrderByPriceAsc();
}
