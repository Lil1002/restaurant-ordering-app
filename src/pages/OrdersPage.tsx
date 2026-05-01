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
        <div style ={{ padding: '1rem'}}>
            <h2>Orders</h2>
            <ul>
                {myOrders.map((order) => (
                    <li key={order.id}>
                        <Link to={`/orders/${order.id}`}>
                            Order #{order.id} -{' '}
                            {new Date().toLocaleString()} - {' '}
                            {order.status ?? 'In Progress'} - Tax ${order.tax.toFixed(2)} - Tip $
                            {order.tip.toFixed(2)}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    )
}