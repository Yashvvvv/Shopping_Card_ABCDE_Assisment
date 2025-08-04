package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.entity.Item;
import com.ecommerce.shoppingcart.service.ItemService;
import com.ecommerce.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item,
                                          @RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (userService.getUserByToken(token).isEmpty()) {
                return ResponseEntity.status(401).build();
            }
        }
        
        Item createdItem = itemService.createItem(item);
        return ResponseEntity.ok(createdItem);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems(@RequestHeader(value = "Authorization", required = false) String token) {
        List<Item> items = itemService.getAllItems();
        System.out.println("ItemController: Returning " + items.size() + " items");
        return ResponseEntity.ok(items);
    }

    @PostMapping("/init")
    public ResponseEntity<String> initializeItems() {
        try {
            itemService.initializeSampleItems();
            return ResponseEntity.ok("Sample items created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating items: " + e.getMessage());
        }
    }
}
