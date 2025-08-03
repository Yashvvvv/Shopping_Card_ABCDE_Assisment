package com.ecommerce.shoppingcart.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class AddToCartRequest {
    @NotEmpty
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
