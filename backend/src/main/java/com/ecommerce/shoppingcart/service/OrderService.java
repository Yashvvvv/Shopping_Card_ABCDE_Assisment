package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.dto.CreateOrderRequest;
import com.ecommerce.shoppingcart.entity.Cart;
import com.ecommerce.shoppingcart.entity.Order;
import com.ecommerce.shoppingcart.repository.CartRepository;
import com.ecommerce.shoppingcart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Order createOrder(Long userId, CreateOrderRequest request) {
        // Verify cart belongs to user
        Optional<Cart> cartOpt = cartRepository.findById(request.getCartId());
        if (cartOpt.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }

        Cart cart = cartOpt.get();
        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("Cart does not belong to user");
        }

        if (!"ACTIVE".equals(cart.getStatus())) {
            throw new RuntimeException("Cart is not active");
        }

        // Create order
        Order order = new Order(cart.getId(), userId);
        order = orderRepository.save(order);

        // Mark cart as completed
        cart.setStatus("COMPLETED");
        cartRepository.save(cart);

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
