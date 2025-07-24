package com.FoodSafetyProject.FoodSafety.repository;

import com.FoodSafetyProject.FoodSafety.entity.BlockchainTransaction;
import com.FoodSafetyProject.FoodSafety.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BlockchainTransactionRepository extends JpaRepository<BlockchainTransaction, Long> {
    BlockchainTransaction findTopByProductOrderByTimestampDesc(Product product);

    List<BlockchainTransaction> findByProductOrderByTimestampAsc(Product product);


}
