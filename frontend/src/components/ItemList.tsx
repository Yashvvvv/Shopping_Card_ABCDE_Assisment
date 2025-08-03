import React, { useState, useEffect } from 'react';
import { Item, itemAPI, cartAPI, orderAPI, AddToCartRequest, CreateOrderRequest, Order } from '../services/api';

interface ItemListProps {
  onLogout: () => void;
  username: string;
}

const ItemList: React.FC<ItemListProps> = ({ onLogout, username }) => {
  const [items, setItems] = useState<Item[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    loadItems();
  }, []);

  const loadItems = async () => {
    try {
      const itemsData = await itemAPI.getAllItems();
      setItems(itemsData);
    } catch (error) {
      window.alert('Error loading items');
    } finally {
      setIsLoading(false);
    }
  };

  const addToCart = async (itemId: number) => {
    try {
      const request: AddToCartRequest = { itemIds: [itemId] };
      await cartAPI.addItemsToCart(request);
      window.alert('Item added to cart successfully!');
    } catch (error) {
      window.alert('Error adding item to cart');
    }
  };

  const showCart = async () => {
    try {
      const cart = await cartAPI.getMyCart();
      const cartItems = await cartAPI.getCartItems(cart.id);
      
      if (cartItems.length === 0) {
        window.alert('Your cart is empty');
        return;
      }

      const cartInfo = cartItems.map(item => `Cart ID: ${item.cartId}, Item ID: ${item.itemId}`).join('\n');
      window.alert(`Cart Items:\n${cartInfo}`);
    } catch (error) {
      window.alert('No active cart found');
    }
  };

  const showOrderHistory = async () => {
    try {
      const orders: Order[] = await orderAPI.getMyOrders();
      
      if (orders.length === 0) {
        window.alert('No orders found');
        return;
      }

      const orderInfo = orders.map(order => `Order ID: ${order.id}`).join('\n');
      window.alert(`Your Orders:\n${orderInfo}`);
    } catch (error) {
      window.alert('Error loading order history');
    }
  };

  const checkout = async () => {
    try {
      const cart = await cartAPI.getMyCart();
      const cartItems = await cartAPI.getCartItems(cart.id);
      
      if (cartItems.length === 0) {
        window.alert('Your cart is empty');
        return;
      }

      const request: CreateOrderRequest = { cartId: cart.id };
      await orderAPI.createOrder(request);
      window.alert('Order successful!');
    } catch (error) {
      window.alert('Error creating order. Please make sure you have items in your cart.');
    }
  };

  if (isLoading) {
    return <div style={styles.loading}>Loading items...</div>;
  }

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h2 style={styles.title}>Welcome, {username}!</h2>
        <div style={styles.headerButtons}>
          <button onClick={checkout} style={{...styles.button, ...styles.checkoutButton}}>
            Checkout
          </button>
          <button onClick={showCart} style={{...styles.button, ...styles.cartButton}}>
            Cart
          </button>
          <button onClick={showOrderHistory} style={{...styles.button, ...styles.orderButton}}>
            Order History
          </button>
          <button onClick={onLogout} style={{...styles.button, ...styles.logoutButton}}>
            Logout
          </button>
        </div>
      </div>

      <div style={styles.itemsContainer}>
        <h3 style={styles.sectionTitle}>Available Items</h3>
        {items.length === 0 ? (
          <p style={styles.noItems}>No items available</p>
        ) : (
          <div style={styles.itemsGrid}>
            {items.map((item) => (
              <div key={item.id} style={styles.itemCard}>
                <h4 style={styles.itemName}>{item.name}</h4>
                <p style={styles.itemStatus}>Status: {item.status}</p>
                <button 
                  onClick={() => addToCart(item.id)}
                  style={styles.addToCartButton}
                  disabled={item.status !== 'ACTIVE'}
                >
                  Add to Cart
                </button>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

const styles = {
  container: {
    padding: '1rem',
    minHeight: '100vh',
    backgroundColor: '#f8f9fa',
  },
  loading: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100vh',
    fontSize: '1.2rem',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '2rem',
    padding: '1rem',
    backgroundColor: 'white',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
  },
  title: {
    margin: 0,
    color: '#333',
  },
  headerButtons: {
    display: 'flex',
    gap: '0.5rem',
  },
  button: {
    padding: '0.5rem 1rem',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '0.9rem',
    fontWeight: 'bold',
  },
  checkoutButton: {
    backgroundColor: '#28a745',
    color: 'white',
  },
  cartButton: {
    backgroundColor: '#17a2b8',
    color: 'white',
  },
  orderButton: {
    backgroundColor: '#6f42c1',
    color: 'white',
  },
  logoutButton: {
    backgroundColor: '#dc3545',
    color: 'white',
  },
  itemsContainer: {
    backgroundColor: 'white',
    padding: '1.5rem',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
  },
  sectionTitle: {
    marginTop: 0,
    marginBottom: '1.5rem',
    color: '#333',
  },
  noItems: {
    textAlign: 'center' as const,
    color: '#666',
    fontSize: '1.1rem',
  },
  itemsGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))',
    gap: '1rem',
  },
  itemCard: {
    border: '1px solid #ddd',
    borderRadius: '8px',
    padding: '1rem',
    backgroundColor: '#f8f9fa',
    transition: 'transform 0.2s',
  },
  itemName: {
    margin: '0 0 0.5rem 0',
    color: '#333',
  },
  itemStatus: {
    margin: '0 0 1rem 0',
    color: '#666',
    fontSize: '0.9rem',
  },
  addToCartButton: {
    width: '100%',
    padding: '0.75rem',
    backgroundColor: '#007bff',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    fontSize: '1rem',
    fontWeight: 'bold',
  },
};

export default ItemList;
