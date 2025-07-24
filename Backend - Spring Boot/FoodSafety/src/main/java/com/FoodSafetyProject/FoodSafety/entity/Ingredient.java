package com.FoodSafetyProject.FoodSafety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ingredient_id;

    private String ingredient_name;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
