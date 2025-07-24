package com.FoodSafetyProject.FoodSafety.service;

import com.FoodSafetyProject.FoodSafety.entity.Product;
import com.FoodSafetyProject.FoodSafety.entity.QrCode;
import com.FoodSafetyProject.FoodSafety.repository.ProductRepository;
import com.FoodSafetyProject.FoodSafety.repository.QrCodeRepository;
import com.FoodSafetyProject.FoodSafety.util.QrCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Value("${app.base-url}")
    private String baseUrl;

    private final ProductRepository productRepository;
    private final QrCodeRepository qrCodeRepository;
    private final BlockchainService blockchainService;

    public ProductService(ProductRepository productRepository,
                          QrCodeRepository qrCodeRepository,
                          BlockchainService blockchainService) {
        this.productRepository = productRepository;
        this.qrCodeRepository = qrCodeRepository;
        this.blockchainService = blockchainService;
    }

    public Product saveProduct(Product product) {
        if (product.getQr_code() == null || product.getQr_code().isEmpty()) {
            String generatedQr = UUID.randomUUID().toString();
            product.setQr_code(generatedQr);
        }

        Product savedProduct = productRepository.save(product);
        String productUrl = baseUrl + "/product-detail?id=" + savedProduct.getProduct_id();

        QrCode qrCode = new QrCode();
        qrCode.setQrCodeValue(productUrl);
        qrCode.setProduct(savedProduct);
        qrCodeRepository.save(qrCode);

        try {
            String qrCodeImagePath = "qrcodes/product_" + savedProduct.getProduct_id() + ".png";
            QrCodeGenerator.generateQrCodeImage(productUrl, qrCodeImagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Blockchain bloklarını veritabanına ekle
        blockchainService.createBlock(savedProduct.getProduct_id(), "Ürün oluşturuldu: " + savedProduct.getProduct_name());
        blockchainService.createBlock(savedProduct.getProduct_id(), "Ürün menşei: " + savedProduct.getOrigin());
        blockchainService.createBlock(savedProduct.getProduct_id(), "İlk kalite kontrol yapıldı");

        return savedProduct;
    }

    // Değiştirildi: Iterable yerine List<Product>
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        existingProduct.setProduct_name(updatedProduct.getProduct_name());
        existingProduct.setOrigin(updatedProduct.getOrigin());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setCreation_date(updatedProduct.getCreation_date());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductByQrCodeValue(String qrCodeValue) {
        QrCode qrCode = qrCodeRepository.findByQrCodeValue(qrCodeValue);
        return Optional.ofNullable(qrCode != null ? qrCode.getProduct() : null);
    }
}
