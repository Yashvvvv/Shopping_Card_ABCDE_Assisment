package com.ecommerce.shoppingcart.config;

import com.ecommerce.shoppingcart.entity.Item;
import com.ecommerce.shoppingcart.entity.User;
import com.ecommerce.shoppingcart.repository.ItemRepository;
import com.ecommerce.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize some sample items
        System.out.println("DataInitializer: Starting data initialization...");
        
        // Create a default user if not present
        if (userRepository.findByUsername("testuser").isEmpty()) {
            System.out.println("DataInitializer: Creating default user...");
            User defaultUser = new User();
            defaultUser.setUsername("testuser");
            defaultUser.setPassword(passwordEncoder.encode("password"));
            User savedUser = userRepository.save(defaultUser);
            System.out.println("DataInitializer: Default user 'testuser' created with ID: " + savedUser.getId());
        } else {
            System.out.println("DataInitializer: Default user 'testuser' already exists.");
        }

        System.out.println("DataInitializer: Current item count: " + itemRepository.count());
        
        if (itemRepository.count() == 0) {
            System.out.println("DataInitializer: No items found, creating sample items...");
            
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
            
            System.out.println("DataInitializer: Created " + itemRepository.count() + " sample items");
        } else {
            System.out.println("DataInitializer: Items already exist, skipping initialization");
        }
        
        System.out.println("DataInitializer: Final item count: " + itemRepository.count());
        System.out.println("DataInitializer: Final user count: " + userRepository.count());
    }
}
