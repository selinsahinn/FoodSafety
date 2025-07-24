package com.FoodSafetyProject.FoodSafety.controller;

import com.FoodSafetyProject.FoodSafety.entity.Product;
import com.FoodSafetyProject.FoodSafety.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.saveProduct(product);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product with ID " + id + " has been deleted successfully.");
    }

    @GetMapping("/by-qr")
    public ResponseEntity<Product> getProductByQrCode(@RequestParam String qr) {
        return productService.getProductByQrCodeValue(qr)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




}
