# Quick Setup Guide

## Prerequisites Check

Before running the application, ensure you have:

### For Backend (Spring Boot):
- **Java 17+** installed
- **Maven 3.6+** installed
- Verify with: `java -version` and `mvn -version`

### For Frontend (React):
- **Node.js 14+** installed
- **npm** installed
- Verify with: `node -version` and `npm -version`

## Installation Steps

### 1. Backend Setup

```bash
# Navigate to backend directory
cd backend

# Install dependencies and compile
mvn clean install

# Run the application
mvn spring-boot:run
```

**Backend will be available at:** `http://localhost:8080`

### 2. Frontend Setup

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
npm start
```

**Frontend will be available at:** `http://localhost:3000`

## Testing the Application

### Using the Web Interface:

1. Open `http://localhost:3000` in your browser
2. Click "Sign Up" to create a new account (e.g., username: `testuser`, password: `password123`)
3. Login with your credentials
4. Browse items and add them to cart
5. Use the Cart button to view cart items
6. Use Checkout to create an order
7. Use Order History to view your orders

### Using API Endpoints:

Import the `Shopping_Cart_API.postman_collection.json` file into Postman and test the endpoints.

### Sample API Testing with curl:

```bash
# 1. Create User
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# 2. Login (save the token from response)
curl -X POST http://localhost:8080/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# 3. Get Items (replace YOUR_TOKEN with actual token)
curl -X GET http://localhost:8080/items \
  -H "Authorization: Bearer YOUR_TOKEN"

# 4. Add to Cart
curl -X POST http://localhost:8080/carts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"itemIds":[1,2,3]}'

# 5. Create Order (replace CART_ID with actual cart ID)
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"cartId":CART_ID}'
```

## Pre-loaded Sample Data

The application comes with sample items:
- Laptop
- Smartphone
- Headphones
- Keyboard
- Mouse
- Monitor
- Webcam
- Tablet

## Troubleshooting

### Common Issues:

1. **Port 8080 already in use:**
   - Stop any other services running on port 8080
   - Or change the port in `application.properties`

2. **Port 3000 already in use:**
   - The React app will prompt to use a different port
   - Or stop other services using port 3000

3. **CORS errors:**
   - Ensure backend is running on port 8080
   - Check CORS configuration in SecurityConfig.java

4. **Token authentication errors:**
   - Make sure to include the full token with "Bearer " prefix
   - Check that the token hasn't expired

5. **Maven/Java not found:**
   - Install Java 17+ and Maven 3.6+
   - Ensure they're added to your system PATH

6. **Node/npm not found:**
   - Install Node.js 14+ which includes npm
   - Ensure they're added to your system PATH

## Database Access

- H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Architecture Overview

```
Frontend (React) ←→ Backend (Spring Boot) ←→ Database (H2)
    :3000              :8080                  (in-memory)
```

The application follows a typical 3-tier architecture with clear separation of concerns.
