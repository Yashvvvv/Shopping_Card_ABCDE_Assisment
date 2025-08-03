import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests if available
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export interface User {
  id: number;
  username: string;
  password?: string;
  token?: string;
  cartId?: number;
  createdAt?: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  userId: number;
  username: string;
}

export interface Item {
  id: number;
  name: string;
  status: string;
  createdAt: string;
}

export interface Cart {
  id: number;
  userId: number;
  name: string;
  status: string;
  createdAt: string;
  cartItems?: CartItem[];
}

export interface CartItem {
  id: number;
  cartId: number;
  itemId: number;
  item?: Item;
}

export interface Order {
  id: number;
  cartId: number;
  userId: number;
  createdAt: string;
}

export interface AddToCartRequest {
  itemIds: number[];
}

export interface CreateOrderRequest {
  cartId: number;
}

// API Functions
export const userAPI = {
  createUser: (user: { username: string; password: string }): Promise<User> =>
    api.post('/users', user).then(res => res.data),
  
  login: (loginRequest: LoginRequest): Promise<LoginResponse> =>
    api.post('/users/login', loginRequest).then(res => res.data),
  
  getAllUsers: (): Promise<User[]> =>
    api.get('/users').then(res => res.data),
};

export const itemAPI = {
  createItem: (item: { name: string }): Promise<Item> =>
    api.post('/items', item).then(res => res.data),
  
  getAllItems: (): Promise<Item[]> =>
    api.get('/items').then(res => res.data),
};

export const cartAPI = {
  addItemsToCart: (request: AddToCartRequest): Promise<Cart> =>
    api.post('/carts', request).then(res => res.data),
  
  getAllCarts: (): Promise<Cart[]> =>
    api.get('/carts').then(res => res.data),
  
  getMyCart: (): Promise<Cart> =>
    api.get('/carts/my-cart').then(res => res.data),
  
  getCartItems: (cartId: number): Promise<CartItem[]> =>
    api.get(`/carts/${cartId}/items`).then(res => res.data),
};

export const orderAPI = {
  createOrder: (request: CreateOrderRequest): Promise<Order> =>
    api.post('/orders', request).then(res => res.data),
  
  getAllOrders: (): Promise<Order[]> =>
    api.get('/orders').then(res => res.data),
  
  getMyOrders: (): Promise<Order[]> =>
    api.get('/orders/my-orders').then(res => res.data),
};
