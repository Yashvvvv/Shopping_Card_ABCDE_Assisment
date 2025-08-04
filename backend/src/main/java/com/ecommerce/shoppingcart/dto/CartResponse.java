package com.ecommerce.shoppingcart.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CartResponse {
    private Long id;
    private Long userId;
    private String name;
    private String status;
    private LocalDateTime createdAt;
    private List<CartItemResponse> cartItems;

    // Constructors
    public CartResponse() {}

    public CartResponse(Long id, Long userId, String name, String status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }
}
