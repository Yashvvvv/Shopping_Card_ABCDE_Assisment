package com.ecommerce.shoppingcart.dto;

public class CreateOrderRequest {
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
