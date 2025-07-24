package com.FoodSafetyProject.FoodSafety.repository;

import com.FoodSafetyProject.FoodSafety.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
