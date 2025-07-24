package com.FoodSafetyProject.FoodSafety.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm endpoint'ler
                .allowedOrigins("http://localhost:3000") // React tarafı
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // Tüm başlıklara izin ver
                .allowCredentials(true); // Çerez/kimlik gönderimi açık
    }
}
