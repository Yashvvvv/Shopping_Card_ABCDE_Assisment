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

    public void initializeSampleItems() {
        System.out.println("ItemService: Initializing sample items...");
        
        if (itemRepository.count() == 0) {
            itemRepository.save(new Item("Laptop"));
            itemRepository.save(new Item("Smartphone"));
            itemRepository.save(new Item("Headphones"));
            itemRepository.save(new Item("Keyboard"));
            itemRepository.save(new Item("Mouse"));
            itemRepository.save(new Item("Monitor"));
            itemRepository.save(new Item("Webcam"));
            itemRepository.save(new Item("Tablet"));
            itemRepository.save(new Item("Gaming Chair"));
            itemRepository.save(new Item("Desk Lamp"));
            itemRepository.save(new Item("USB Cable"));
            itemRepository.save(new Item("Power Bank"));
            
            System.out.println("ItemService: Created " + itemRepository.count() + " sample items");
        } else {
            System.out.println("ItemService: Items already exist (" + itemRepository.count() + " items)");
        }
    }
}
