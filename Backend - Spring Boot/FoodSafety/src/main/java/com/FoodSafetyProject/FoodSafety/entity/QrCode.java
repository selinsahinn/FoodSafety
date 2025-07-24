package com.FoodSafetyProject.FoodSafety.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "qr_codes")
public class QrCode {

    // Getter ve Setter'lar
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qr_code_id") // Veritabanı ismi aynı kalır
    private int qrCodeId;

    @Column(name = "qr_code_value", unique = true)
    private String qrCodeValue;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

}
