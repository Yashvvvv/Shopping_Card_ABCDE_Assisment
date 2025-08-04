package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.dto.LoginRequest;
import com.ecommerce.shoppingcart.dto.LoginResponse;
import com.ecommerce.shoppingcart.entity.User;
import com.ecommerce.shoppingcart.repository.UserRepository;
import com.ecommerce.shoppingcart.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User createUser(User user) {
        System.out.println("Creating user: " + user.getUsername());
        
        if (userRepository.existsByUsername(user.getUsername())) {
            System.out.println("Username already exists: " + user.getUsername());
            throw new RuntimeException("Username already exists");
        }
        
        String originalPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        System.out.println("Original password: " + originalPassword);
        System.out.println("Encoded password: " + encodedPassword);
        
        User savedUser = userRepository.save(user);
        System.out.println("User created successfully: " + savedUser.getUsername());
        
        return savedUser;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println("Login attempt for username: " + loginRequest.getUsername());
        
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
        if (userOpt.isEmpty()) {
            System.out.println("User not found: " + loginRequest.getUsername());
            throw new RuntimeException("Invalid username or password");
        }

        User user = userOpt.get();
        System.out.println("User found: " + user.getUsername());
        System.out.println("Password check: " + passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()));
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            System.out.println("Password mismatch for user: " + loginRequest.getUsername());
            throw new RuntimeException("Invalid username or password");
        }

        // Clear any existing token for single device login
        String token = jwtUtil.generateToken(user.getUsername(), user.getId());
        user.setToken(token);
        userRepository.save(user);

        System.out.println("Login successful for user: " + user.getUsername());
        return new LoginResponse(token, user.getId(), user.getUsername());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByToken(String token) {
        return userRepository.findByToken(token);
    }

    public Long getUserIdFromToken(String token) {
        try {
            return jwtUtil.getUserIdFromToken(token);
        } catch (Exception e) {
            System.err.println("Error extracting user ID from token: " + e.getMessage());
            return null;
        }
    }
}
