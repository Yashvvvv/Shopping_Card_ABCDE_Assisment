package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.entity.Item;
import com.ecommerce.shoppingcart.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getActiveItems() {
        return itemRepository.findByStatus("ACTIVE");
    }
}
