package com.FoodSafetyProject.FoodSafety.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // kullanıcı adı benzersiz olmalı
    private String username;

    private String password;

    /**
     * Kullanıcının rolü, örneğin:
     * "ROLE_ADMIN", "ROLE_SELLER", "ROLE_PRODUCER"
     * Guest kullanıcı için User entity kullanılmaz, anonimdir.
     */
    @Column(nullable = false)
    private String role;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
