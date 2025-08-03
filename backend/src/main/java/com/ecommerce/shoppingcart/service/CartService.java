package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.dto.AddToCartRequest;
import com.ecommerce.shoppingcart.entity.Cart;
import com.ecommerce.shoppingcart.entity.CartItem;
import com.ecommerce.shoppingcart.entity.User;
import com.ecommerce.shoppingcart.repository.CartRepository;
import com.ecommerce.shoppingcart.repository.CartItemRepository;
import com.ecommerce.shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Cart addItemsToCart(Long userId, AddToCartRequest request) {
        // Find or create active cart for user
        Optional<Cart> existingCart = cartRepository.findByUserIdAndStatus(userId, "ACTIVE");
        Cart cart;
        
        if (existingCart.isPresent()) {
            cart = existingCart.get();
        } else {
            cart = new Cart(userId, "User Cart");
            cart = cartRepository.save(cart);
            
            // Update user's current cart_id
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setCartId(cart.getId());
                userRepository.save(user);
            }
        }

        // Add items to cart
        for (Long itemId : request.getItemIds()) {
            Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), itemId);
            if (existingItem.isEmpty()) {
                CartItem cartItem = new CartItem(cart.getId(), itemId);
                cartItemRepository.save(cartItem);
            }
        }

        return cart;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public List<Cart> getCartsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Optional<Cart> getActiveCartByUserId(Long userId) {
        return cartRepository.findByUserIdAndStatus(userId, "ACTIVE");
    }

    public List<CartItem> getCartItems(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }
}
