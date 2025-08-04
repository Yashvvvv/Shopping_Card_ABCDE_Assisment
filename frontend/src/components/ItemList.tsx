import React, { useState, useEffect } from 'react';
import { itemAPI, cartAPI, orderAPI } from '../services/api';
import './ItemList.css';

interface Item {
  id: number;
  name: string;
  status: string;
  createdAt: string;
}

interface ItemListProps {
  onLogout: () => void;
  username: string;
}

const ItemList: React.FC<ItemListProps> = ({ onLogout, username }) => {
  const [items, setItems] = useState<Item[]>([]);
  const [cart, setCart] = useState<any>(null);
  const [orders, setOrders] = useState<any[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [cartItemCount, setCartItemCount] = useState(0);

  useEffect(() => {
    loadItems();
    loadCart();
  }, []);

  const loadItems = async () => {
    try {
      const response = await itemAPI.getAllItems();
      setItems(response);
    } catch (error) {
      console.error('Failed to load items', error);
    }
  };

  const loadCart = async () => {
    try {
      const response = await cartAPI.getMyCart();
      setCart(response);
      setCartItemCount(response?.cartItems?.length || 0);
    } catch (error) {
      setCart(null);
      setCartItemCount(0);
    }
  };

  const loadOrders = async () => {
    try {
      const response = await orderAPI.getAllOrders();
      setOrders(response);
    } catch (error) {
      console.error('Failed to load orders', error);
    }
  };

  const addItemToCart = async (itemId: number) => {
    try {
      setIsLoading(true);
      await cartAPI.addItemsToCart({ itemIds: [itemId] });
      await loadCart(); // Reload cart to update count
      alert('Item added to cart successfully!');
    } catch (error) {
      alert('Failed to add item to cart');
      console.error('Error adding item to cart:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const showCartItems = async () => {
    try {
      if (!cart || !cart.cartItems || cart.cartItems.length === 0) {
        alert('Your cart is empty!');
        return;
      }
      
      const cartDetails = cart.cartItems.map((item: any) => 
        `Cart ID: ${item.cartId}, Item ID: ${item.itemId}, Item: ${item.itemName}`
      ).join('\n');
      
      alert(`Cart Items:\n${cartDetails}`);
    } catch (error) {
      alert('Failed to load cart items');
    }
  };

  const showOrderHistory = async () => {
    try {
      await loadOrders();
      if (orders.length === 0) {
        alert('No orders found!');
        return;
      }
      
      const orderIds = orders.map(order => `Order ID: ${order.id}`).join('\n');
      alert(`Order History:\n${orderIds}`);
    } catch (error) {
      alert('Failed to load order history');
    }
  };

  const checkout = async () => {
    try {
      if (!cart || !cart.cartItems || cart.cartItems.length === 0) {
        alert('Your cart is empty! Add some items before checkout.');
        return;
      }

      setIsLoading(true);
      await orderAPI.createOrder({ cartId: cart.id });
      
      // Reload data after successful order
      await loadCart();
      await loadOrders();
      
      alert('Order successful!');
    } catch (error) {
      alert('Failed to create order');
      console.error('Error creating order:', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="item-list-container">
      {/* Header */}
      <header className="header">
        <div className="header-left">
          <h1>🛍️ E-Commerce Store</h1>
          <p>Welcome, {username}!</p>
        </div>
        <div className="header-right">
          <button className="logout-btn" onClick={onLogout}>
            Logout
          </button>
        </div>
      </header>

      {/* Action Buttons - As per requirements */}
      <div className="action-buttons">
        <button 
          className="checkout-btn" 
          onClick={checkout} 
          disabled={isLoading || cartItemCount === 0}
        >
          {isLoading ? 'Processing...' : `Checkout ${cartItemCount > 0 ? `(${cartItemCount} items)` : ''}`}
        </button>
        
        <button 
          className="cart-btn" 
          onClick={showCartItems}
        >
          Cart {cartItemCount > 0 && `(${cartItemCount})`}
        </button>
        
        <button 
          className="order-history-btn" 
          onClick={showOrderHistory}
        >
          Order History
        </button>
      </div>

      {/* Items List - Main Screen */}
      <main className="items-section">
        <h2>Available Items</h2>
        <div className="items-grid">
          {items.map((item) => (
            <div key={item.id} className="item-card">
              <div className="item-info">
                <h3>{item.name}</h3>
                <p className="item-status">Status: {item.status}</p>
                <p className="item-id">ID: {item.id}</p>
              </div>
              <button
                className="add-to-cart-btn"
                onClick={() => addItemToCart(item.id)}
                disabled={isLoading || item.status !== 'ACTIVE'}
              >
                {isLoading ? 'Adding...' : 'Add to Cart'}
              </button>
            </div>
          ))}
        </div>
        
        {items.length === 0 && (
          <div className="no-items">
            <p>No items available at the moment.</p>
          </div>
        )}
      </main>
    </div>
  );
};

export default ItemList;
