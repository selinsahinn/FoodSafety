package com.FoodSafetyProject.FoodSafety.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "blockchain_transactions")
public class BlockchainTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    private String previousHash;
    private String currentHash;
    private String data;

    private LocalDateTime timestamp;

    // 🔴 timestampMillis tamamen kaldırıldı!

    /**
     * Calculates the SHA-256 hash for this transaction using product ID, data,
     * previous hash, and timestamp in milliseconds.
     */
    public String calculateHash() {
        long millis = timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String rawData = product.getProduct_id() + data + previousHash + millis;
        return DigestUtils.sha256Hex(rawData);
    }
}
