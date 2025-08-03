package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.dto.AddToCartRequest;
import com.ecommerce.shoppingcart.entity.Cart;
import com.ecommerce.shoppingcart.entity.CartItem;
import com.ecommerce.shoppingcart.service.CartService;
import com.ecommerce.shoppingcart.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Cart> addItemsToCart(@Valid @RequestBody AddToCartRequest request,
                                              @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            Long userId = userService.getUserIdFromToken(token);
            if (userId == null || userService.getUserByToken(token).isEmpty()) {
                return ResponseEntity.status(401).build();
            }

            Cart cart = cartService.addItemsToCart(userId, request);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
    public ResponseEntity<Cart> getMyCart(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            Long userId = userService.getUserIdFromToken(token);
            if (userId == null || userService.getUserByToken(token).isEmpty()) {
                return ResponseEntity.status(401).build();
            }

            Optional<Cart> cart = cartService.getActiveCartByUserId(userId);
            if (cart.isPresent()) {
                return ResponseEntity.ok(cart.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId,
                                                      @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            Long userId = userService.getUserIdFromToken(token);
            if (userId == null || userService.getUserByToken(token).isEmpty()) {
                return ResponseEntity.status(401).build();
            }

            List<CartItem> cartItems = cartService.getCartItems(cartId);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
