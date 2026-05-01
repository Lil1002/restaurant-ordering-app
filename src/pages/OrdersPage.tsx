import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getOrders, type Order} from '../api/orders';
import { useAuth } from '../context/AuthContext';

export default function OrdersPage() {
    const { user } = useAuth();
    const navigate = useNavigate(); 
    const [orders, setOrders] = useState<Order[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!user) return;
        getOrders()
            .then(setOrders)
            .catch((e) => setError(e.message));
    }, [user]);

    if(!user) {
        navigate('/login');
        return null;
    }

    if (error) {
        return (
            <div style={{ padding: '1rem'}}>
                Failed to load orders: {error}
            </div>
        );
    }

    const myOrders = orders.filter((o) => o.userid === user.id);

    if (!myOrders.length) {
        return (
            <div style={{ padding: '1rem'}}>
                No orders found.
            </div>
        );
    }

    return (
        <div className="checkout-page">
  <div className="checkout-card">
    <h2>Orders</h2>

    {myOrders.length === 0 ? (
      <p className="empty-text">You have no orders yet.</p>
    ) : (
      <ul className="order-list">
        {myOrders.map(order => (
          <li className="order-item" key={order.id}>
            <Link to={`/orders/${order.id}`} className="order-link">
              <div className="order-main">
                <div>
                  <div className="order-id">Order #{order.id}</div>
                  <div className="order-meta">
                    {new Date().toLocaleString()} •{' '}
                    <span className="order-status">
                      {order.status ?? 'In Progress'}
                    </span>
                  </div>
                </div>
              </div>
              <div className="order-amounts">
                <div>Tax: ${order.tax.toFixed(2)}</div>
                <div>Tip: ${order.tip.toFixed(2)}</div>
              </div>
            </Link>
          </li>
        ))}
      </ul>
    )}
  </div>
</div>
    )
}