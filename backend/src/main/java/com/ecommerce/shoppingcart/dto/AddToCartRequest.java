package com.ecommerce.shoppingcart.dto;

import java.util.List;

public class AddToCartRequest {
    private List<Long> itemIds;

    // Constructors
    public AddToCartRequest() {}

    public AddToCartRequest(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    // Getters and Setters
    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
