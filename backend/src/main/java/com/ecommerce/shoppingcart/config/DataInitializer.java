package com.ecommerce.shoppingcart.config;

import com.ecommerce.shoppingcart.entity.Item;
import com.ecommerce.shoppingcart.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize some sample items
        if (itemRepository.count() == 0) {
            itemRepository.save(new Item("Laptop"));
            itemRepository.save(new Item("Smartphone"));
            itemRepository.save(new Item("Headphones"));
            itemRepository.save(new Item("Keyboard"));
            itemRepository.save(new Item("Mouse"));
            itemRepository.save(new Item("Monitor"));
            itemRepository.save(new Item("Webcam"));
            itemRepository.save(new Item("Tablet"));
        }
    }
}
