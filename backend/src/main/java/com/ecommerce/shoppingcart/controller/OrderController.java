package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.dto.CreateOrderRequest;
import com.ecommerce.shoppingcart.entity.Order;
import com.ecommerce.shoppingcart.entity.User;
import com.ecommerce.shoppingcart.repository.UserRepository;
import com.ecommerce.shoppingcart.service.OrderService;
import com.ecommerce.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User resolveUser(String token) {
        if (token != null && !token.isEmpty()) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            Long tokenUserId = userService.getUserIdFromToken(token);
            if (tokenUserId != null) {
                return userRepository.findById(tokenUserId).orElse(null);
            }
        }
        return userRepository.findByUsername("testuser").orElse(null);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request,
                                           @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            User user = resolveUser(token);
            
            if (user == null) {
                return ResponseEntity.status(500).body(Map.of("error", "No valid user found for order operation"));
            }

            Long userId = user.getId();
            Order order = orderService.createOrder(userId, request);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            System.err.println("Error creating order: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create order: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (userService.getUserByToken(token).isEmpty()) {
                return ResponseEntity.status(401).build();
            }
        }
        
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/my-orders")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getMyOrders(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            User user = resolveUser(token);
            
            if (user == null) {
                return ResponseEntity.status(500).body(Map.of("error", "No valid user found for order operation"));
            }

            Long userId = user.getId();
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            System.err.println("Error getting orders: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to get orders: " + e.getMessage()));
        }
    }
}
