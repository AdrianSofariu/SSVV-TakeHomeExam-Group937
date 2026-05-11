package com.ssvv.inventory.repository;

import com.ssvv.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.stockLevel < p.minThreshold")
    List<Product> findProductsBelowMinThreshold();
}