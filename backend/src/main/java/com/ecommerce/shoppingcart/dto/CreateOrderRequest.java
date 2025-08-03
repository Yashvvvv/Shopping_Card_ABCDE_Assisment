package com.ecommerce.shoppingcart.dto;

import jakarta.validation.constraints.NotNull;

public class CreateOrderRequest {
    @NotNull
    private Long cartId;

    // Constructors
    public CreateOrderRequest() {}

    public CreateOrderRequest(Long cartId) {
        this.cartId = cartId;
    }

    // Getters and Setters
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}
