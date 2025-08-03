package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateUser() {
        User user = new User("testuser123", "password123");
        
        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
