package com.FoodSafetyProject.FoodSafety.repository;

import com.FoodSafetyProject.FoodSafety.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}