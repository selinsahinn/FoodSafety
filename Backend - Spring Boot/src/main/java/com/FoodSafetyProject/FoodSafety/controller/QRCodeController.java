package com.FoodSafetyProject.FoodSafety.controller;

import com.FoodSafetyProject.FoodSafety.dto.ProductDTO;
import com.FoodSafetyProject.FoodSafety.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @RequestMapping(value = "/generate", method = {RequestMethod.GET, RequestMethod.POST})
    public String generateQRCode(@RequestParam String data, @RequestParam int productId) {
        return qrCodeService.generateAndSaveQRCode(data, (long) productId);
    }


    @GetMapping("/product-info")
    public ResponseEntity<?> getProductInfoByQr(@RequestParam String qr) {
        ProductDTO product = qrCodeService.getProductByQrCode(qr);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for given QR code.");
        }
        return ResponseEntity.ok(product);
    }

}
