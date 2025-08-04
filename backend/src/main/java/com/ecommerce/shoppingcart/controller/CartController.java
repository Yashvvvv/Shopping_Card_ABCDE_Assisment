package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.dto.AddToCartRequest;
import com.ecommerce.shoppingcart.dto.CartResponse;
import com.ecommerce.shoppingcart.dto.CartItemResponse;
import com.ecommerce.shoppingcart.entity.Cart;
import com.ecommerce.shoppingcart.entity.CartItem;
import com.ecommerce.shoppingcart.entity.User;
import com.ecommerce.shoppingcart.repository.UserRepository;
import com.ecommerce.shoppingcart.service.CartService;
import com.ecommerce.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class CartController {

    @Autowired
    private CartService cartService;

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
    public ResponseEntity<?> addItemsToCart(@RequestBody AddToCartRequest request,
                                              @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            User user = resolveUser(token);

            if (user == null) {
                System.out.println("No valid user found for cart operation!");
                return ResponseEntity.status(500).body(Map.of("error", "No valid user found for cart operation"));
            }

            Long userId = user.getId();

            System.out.println("Adding items to cart for user ID: " + userId);
            System.out.println("Item IDs: " + request.getItemIds());

            if (request.getItemIds() == null || request.getItemIds().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "No items provided"));
            }

            Cart cart = cartService.addItemsToCart(userId, request);
            
            Map<String, Object> response = Map.of(
                "id", cart.getId(),
                "userId", cart.getUserId(),
                "name", cart.getName(),
                "status", cart.getStatus(),
                "message", "Items added to cart successfully"
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error adding items to cart: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to add items to cart: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (userService.getUserByToken(token).isEmpty()) {
                return ResponseEntity.status(401).build();
            }
        }
        
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/my-cart")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getMyCart(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            User user = resolveUser(token);

            if (user == null) {
                return ResponseEntity.status(500).body(Map.of("error", "No valid user found for cart operation"));
            }

            Long userId = user.getId();

            Optional<Cart> cartOpt = cartService.getActiveCartByUserId(userId);
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                
                // Create DTO to avoid JSON serialization issues
                CartResponse cartResponse = new CartResponse(
                    cart.getId(),
                    cart.getUserId(),
                    cart.getName(),
                    cart.getStatus(),
                    cart.getCreatedAt()
                );
                
                // Map cart items to DTOs
                List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
                    .map(cartItem -> new CartItemResponse(
                        cartItem.getId(),
                        cartItem.getCartId(),
                        cartItem.getItemId(),
                        cartItem.getItem() != null ? cartItem.getItem().getName() : "Unknown Item",
                        cartItem.getItem() != null ? cartItem.getItem().getStatus() : "UNKNOWN"
                    ))
                    .collect(Collectors.toList());
                
                cartResponse.setCartItems(cartItemResponses);
                
                return ResponseEntity.ok(cartResponse);
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "No active cart found"));
            }
        } catch (Exception e) {
            System.err.println("Error getting user cart: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to get cart: " + e.getMessage()));
        }
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<?> getCartItems(@PathVariable Long cartId,
                                                      @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            Long userId = userService.getUserIdFromToken(token);
            if (userId == null || userService.getUserByToken(token).isEmpty()) {
                return ResponseEntity.status(401).body("{\"error\": \"Unauthorized\"}");
            }

            List<CartItem> cartItems = cartService.getCartItems(cartId);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            System.err.println("Error getting cart items: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
