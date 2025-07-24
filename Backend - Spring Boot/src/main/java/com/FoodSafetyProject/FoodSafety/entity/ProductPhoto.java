package com.FoodSafetyProject.FoodSafety.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "product_photos")
public class ProductPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photo_id;

    private String photo_url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
