package com.FoodSafetyProject.FoodSafety.service;

import com.FoodSafetyProject.FoodSafety.entity.BlockchainTransaction;
import com.FoodSafetyProject.FoodSafety.entity.Product;
import com.FoodSafetyProject.FoodSafety.repository.BlockchainTransactionRepository;
import com.FoodSafetyProject.FoodSafety.repository.ProductRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class BlockchainService {

    private final BlockchainTransactionRepository blockchainRepo;
    private final ProductRepository productRepository;

    public BlockchainService(BlockchainTransactionRepository blockchainRepo,
                             ProductRepository productRepository) {
        this.blockchainRepo = blockchainRepo;
        this.productRepository = productRepository;
    }

    public BlockchainTransaction createBlock(Long productId, String data) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        BlockchainTransaction lastBlock = blockchainRepo.findTopByProductOrderByTimestampDesc(product);
        String previousHash = (lastBlock != null) ? lastBlock.getCurrentHash() : "0";

        LocalDateTime now = LocalDateTime.now();
        long nowMillis = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        BlockchainTransaction block = new BlockchainTransaction();
        block.setProduct(product);
        block.setData(data);
        block.setPreviousHash(previousHash);
        block.setTimestamp(now);

        String rawData = product.getProduct_id() + data + previousHash + nowMillis;
        String currentHash = DigestUtils.sha256Hex(rawData);
        block.setCurrentHash(currentHash);

        return blockchainRepo.save(block);
    }

    public List<BlockchainTransaction> getAllBlocks() {
        return blockchainRepo.findAll();
    }

    public boolean isProductChainValid(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        List<BlockchainTransaction> blocks = blockchainRepo.findByProductOrderByTimestampAsc(product);

        for (int i = 1; i < blocks.size(); i++) {
            BlockchainTransaction prev = blocks.get(i - 1);
            BlockchainTransaction curr = blocks.get(i);

            if (!curr.getPreviousHash().equals(prev.getCurrentHash())) {
                return false;
            }

            long millis = curr.getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            String rawData = product.getProduct_id() + curr.getData() + curr.getPreviousHash() + millis;
            String recalculated = DigestUtils.sha256Hex(rawData);
            if (!recalculated.equals(curr.getCurrentHash())) {
                return false;
            }
        }
        return true;
    }
}
