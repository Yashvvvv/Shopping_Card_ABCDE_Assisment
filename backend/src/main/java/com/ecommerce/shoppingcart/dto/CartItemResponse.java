package com.ecommerce.shoppingcart.dto;

public class CartItemResponse {
    private Long id;
    private Long cartId;
    private Long itemId;
    private String itemName;
    private String itemStatus;

    // Constructors
    public CartItemResponse() {}

    public CartItemResponse(Long id, Long cartId, Long itemId, String itemName, String itemStatus) {
        this.id = id;
        this.cartId = cartId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemStatus = itemStatus;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
}
