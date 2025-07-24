package com.FoodSafetyProject.FoodSafety.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class BlockchainDTO {
    private Long id;
    private String data;
    private String previousHash;
    private String currentHash;
    private LocalDateTime timestamp;
    private Long productId;
}
