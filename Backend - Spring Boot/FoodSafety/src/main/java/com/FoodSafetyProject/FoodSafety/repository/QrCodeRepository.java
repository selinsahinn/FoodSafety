package com.FoodSafetyProject.FoodSafety.repository;

import com.FoodSafetyProject.FoodSafety.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<QrCode, Integer> {
    QrCode findByQrCodeValue(String qrCodeValue);


}
