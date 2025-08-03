# E-Commerce Shopping Cart Application

A full-stack e-commerce shopping cart application built with Spring Boot backend and React frontend.

## Project Structure

```
Assisment_01/
├── backend/          # Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ecommerce/shoppingcart/
│   │   │   │   ├── entity/       # JPA entities
│   │   │   │   ├── repository/   # Data repositories
│   │   │   │   ├── service/      # Business logic
│   │   │   │   ├── controller/   # REST controllers
│   │   │   │   ├── dto/          # Data transfer objects
│   │   │   │   ├── config/       # Configuration classes
│   │   │   │   └── util/         # Utility classes
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
└── frontend/         # React application
    ├── src/
    │   ├── components/   # React components
    │   ├── services/     # API service layer
    │   └── App.tsx
    └── package.json
```

## Features

### Backend Features
- User registration and authentication with JWT tokens
- Single device login (one token per user)
- Item management
- Shopping cart functionality
- Order creation and management
- RESTful API endpoints
- H2 in-memory database
- CORS configuration for frontend integration

### Frontend Features
- User login/signup interface
- Items listing with add to cart functionality
- Cart management (view cart items)
- Order history tracking
- Checkout process
- Responsive design
- Local storage for session management

## API Endpoints

### User Endpoints
- `POST /users` - Create a new user
- `GET /users` - List all users
- `POST /users/login` - User login

### Item Endpoints
- `POST /items` - Create an item
- `GET /items` - List all items

### Cart Endpoints
- `POST /carts` - Add items to cart (requires token)
- `GET /carts` - List all carts
- `GET /carts/my-cart` - Get user's active cart
- `GET /carts/{cartId}/items` - Get cart items

### Order Endpoints
- `POST /orders` - Create order from cart (requires token)
- `GET /orders` - List all orders
- `GET /orders/my-orders` - Get user's orders

## Database Schema

### Tables

#### users
- `id` (Primary Key)
- `username` (Unique)
- `password` (Encrypted)
- `token`
- `cart_id` (Foreign Key)
- `created_at`

#### items
- `id` (Primary Key)
- `name`
- `status`
- `created_at`

#### carts
- `id` (Primary Key)
- `user_id` (Foreign Key)
- `name`
- `status`
- `created_at`

#### cart_items
- `id` (Primary Key)
- `cart_id` (Foreign Key)
- `item_id` (Foreign Key)

#### orders
- `id` (Primary Key)
- `cart_id` (Foreign Key)
- `user_id` (Foreign Key)
- `created_at`

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Node.js 14 or higher
- npm or yarn

## Installation and Setup

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

The frontend will start on `http://localhost:3000`

## Usage

### 1. User Registration and Login
- Open the frontend application at `http://localhost:3000`
- Create a new account using the "Sign Up" button
- Login with your credentials

### 2. Shopping Flow
- After login, you'll see the items list
- Click on any item to add it to your cart
- Use the "Cart" button to view items in your cart
- Use the "Checkout" button to convert your cart to an order
- Use the "Order History" button to view your past orders

### 3. API Testing
- The H2 console is available at `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## Sample Data

The application automatically creates sample items on startup:
- Laptop
- Smartphone
- Headphones
- Keyboard
- Mouse
- Monitor
- Webcam
- Tablet

## Testing

### Manual Testing Steps

1. **User Registration:**
   ```bash
   curl -X POST http://localhost:8080/users \
   -H "Content-Type: application/json" \
   -d '{"username":"testuser","password":"password123"}'
   ```

2. **User Login:**
   ```bash
   curl -X POST http://localhost:8080/users/login \
   -H "Content-Type: application/json" \
   -d '{"username":"testuser","password":"password123"}'
   ```

3. **Get Items:**
   ```bash
   curl -X GET http://localhost:8080/items \
   -H "Authorization: Bearer YOUR_TOKEN_HERE"
   ```

4. **Add Items to Cart:**
   ```bash
   curl -X POST http://localhost:8080/carts \
   -H "Content-Type: application/json" \
   -H "Authorization: Bearer YOUR_TOKEN_HERE" \
   -d '{"itemIds":[1,2,3]}'
   ```

5. **Create Order:**
   ```bash
   curl -X POST http://localhost:8080/orders \
   -H "Content-Type: application/json" \
   -H "Authorization: Bearer YOUR_TOKEN_HERE" \
   -d '{"cartId":1}'
   ```

## Security Features

- JWT token-based authentication
- Password encryption using BCrypt
- Single device login enforcement
- CORS configuration for cross-origin requests
- Token validation on protected endpoints

## Technology Stack

### Backend
- **Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **Database:** H2 (in-memory)
- **ORM:** Spring Data JPA
- **Security:** Spring Security + JWT
- **Build Tool:** Maven

### Frontend
- **Framework:** React 18
- **Language:** TypeScript
- **HTTP Client:** Axios
- **Build Tool:** Create React App

## Future Enhancements

- Add inventory management
- Implement item categories
- Add shopping cart quantity management
- Implement order status tracking
- Add payment integration
- Implement user profiles
- Add product images and descriptions
- Implement search and filtering

## Troubleshooting

### Common Issues

1. **CORS Errors:** Ensure the backend CORS configuration allows `http://localhost:3000`
2. **Authentication Errors:** Check that the JWT token is properly set in the Authorization header
3. **Database Issues:** H2 database resets on application restart (by design)
4. **Port Conflicts:** Ensure ports 8080 (backend) and 3000 (frontend) are available

### Support

For issues or questions, please check the application logs and ensure all prerequisites are met.
