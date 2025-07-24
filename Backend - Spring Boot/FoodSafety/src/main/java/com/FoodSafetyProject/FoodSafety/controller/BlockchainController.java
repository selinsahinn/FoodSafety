package com.FoodSafetyProject.FoodSafety.controller;

import com.FoodSafetyProject.FoodSafety.dto.BlockchainDTO;
import com.FoodSafetyProject.FoodSafety.entity.BlockchainTransaction;
import com.FoodSafetyProject.FoodSafety.repository.BlockchainTransactionRepository;
import com.FoodSafetyProject.FoodSafety.service.BlockchainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    private final BlockchainTransactionRepository blockchainRepo;


    private final BlockchainService blockchainService;

    public BlockchainController(BlockchainTransactionRepository blockchainRepo,
                                BlockchainService blockchainService) {
        this.blockchainRepo = blockchainRepo;
        this.blockchainService = blockchainService;
    }


    @PostMapping("/add")
    public BlockchainTransaction addBlock(@RequestBody BlockchainDTO blockchainDTO) {
        return blockchainService.createBlock(blockchainDTO.getProductId(), blockchainDTO.getData());
    }


    @GetMapping("/all")
    public List<BlockchainDTO> getAll() {
        return blockchainRepo.findAll().stream().map(tx -> {
            BlockchainDTO dto = new BlockchainDTO();
            dto.setId(tx.getId());
            dto.setData(tx.getData());
            dto.setPreviousHash(tx.getPreviousHash());
            dto.setCurrentHash(tx.getCurrentHash());
            dto.setTimestamp(tx.getTimestamp());
            dto.setProductId(tx.getProduct().getProduct_id()); // ← burada düzeltme yapıldı
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/verify/{productId}")
    public ResponseEntity<Map<String, Object>> verifyProductChain(@PathVariable Long productId) {
        boolean isValid = blockchainService.isProductChainValid(productId);

        Map<String, Object> response = new HashMap<>();
        response.put("productId", productId);
        response.put("isValid", isValid);

        if (isValid) {
            response.put("message", "Blockchain zinciri geçerli. Ürün güvenlidir.");
        } else {
            response.put("message", "Blockchain zinciri geçersiz! Ürün güvensiz olabilir.");
        }

        return ResponseEntity.ok(response);
    }




}
