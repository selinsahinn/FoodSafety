package com.FoodSafetyProject.FoodSafety.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductDTO {
    // Getter - Setter
    private String productName;
    private String origin;
    private String description;
    private List<String> ingredients;

}
