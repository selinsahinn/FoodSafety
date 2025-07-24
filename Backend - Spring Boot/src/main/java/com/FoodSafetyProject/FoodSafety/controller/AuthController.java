package com.FoodSafetyProject.FoodSafety.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/role")
    public ResponseEntity<String> getUserRole(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.ok("ANONIM");
        }

        return ResponseEntity.ok(authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.startsWith("ROLE_"))
                .map(r -> r.substring(5)) // ROLE_ kısmını çıkar
                .findFirst()
                .orElse("ANONIM"));
    }
}

