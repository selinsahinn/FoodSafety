package com.FoodSafetyProject.FoodSafety.service;

import com.FoodSafetyProject.FoodSafety.dto.ProductDTO;
import com.FoodSafetyProject.FoodSafety.entity.Ingredient;
import com.FoodSafetyProject.FoodSafety.entity.Product;
import com.FoodSafetyProject.FoodSafety.entity.QrCode;
import com.FoodSafetyProject.FoodSafety.repository.ProductRepository;
import com.FoodSafetyProject.FoodSafety.repository.QrCodeRepository;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QRCodeService {

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Autowired
    private ProductRepository productRepository;

    public String generateAndSaveQRCode(String data, Long productId) {
        try {
            // QR klasörü varsa geç, yoksa oluştur
            File directory = new File("qrcodes");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = "qrcodes/product_" + productId + ".png";

            // QR kod oluştur
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300, hints);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            // Veritabanına kaydet
            Product product = productRepository.findById(productId).orElse(null);
            if (product == null) {
                return "Product not found!";
            }

            QrCode qrCode = new QrCode();
            qrCode.setQrCodeValue(data);
            qrCode.setProduct(product);

            qrCodeRepository.save(qrCode);

            return "QR Code generated and saved: " + filePath;
        } catch (Exception e) {
            return "Error generating QR Code: " + e.getMessage();
        }
    }

    public ProductDTO getProductByQrCode(String qrCodeValue) {
        QrCode qrCode = qrCodeRepository.findByQrCodeValue(qrCodeValue);
        if (qrCode == null || qrCode.getProduct() == null) {
            return null;
        }

        Product product = qrCode.getProduct();

        ProductDTO dto = new ProductDTO();
        dto.setProductName(product.getProduct_name());
        dto.setOrigin(product.getOrigin());
        dto.setDescription(product.getDescription());

        List<String> ingredients = product.getIngredients()
                .stream()
                .map(i -> i.getIngredient_name() + " (" + i.getQuantity() + ")")
                .collect(Collectors.toList());

        dto.setIngredients(ingredients);

        return dto;
    }
}
