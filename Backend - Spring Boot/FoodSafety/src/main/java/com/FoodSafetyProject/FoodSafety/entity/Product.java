package com.FoodSafetyProject.FoodSafety.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @Column(nullable = false)
    private String product_name;

    @Column(nullable = false)
    private String origin;

    private LocalDateTime creation_date;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(unique = true)
    private String qr_code;

    // İlişkiler
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPhoto> photos;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<BlockchainTransaction> blockchainTransactions;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<QrCode> qrCodes;

    // Getter ve Setter'lar (Lombok kullanıyorsan @Data ekleyebilirsin)
}
