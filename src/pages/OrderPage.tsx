import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getMenuItems, type MenuItem } from '../api/menu';
import { getOrderById, getOrderItems, type OrderItem, type Order } from '../api/orders';

export default function OrderPage() {
    const { orderId } = useParams();
    const [order, setOrder] = useState<Order | null>(null);
    const [items, setItems] = useState<OrderItem[]>([]);
    const [menuById, setMenuById] = useState<Record<number, MenuItem>>({});
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!orderId) return;
        const id = Number(orderId);
        if (Number.isNaN(id)) {
            setError('Invalid order id');
            return;
        }

        Promise.all([getOrderById(id), getOrderItems(id), getMenuItems()])
            .then(([orderData, itemData, menuData]) => {
                setOrder(orderData);
                setItems(itemData);
                const map: Record<number, MenuItem> = {};
                menuData.forEach((m) => {
                    map[m.id] = m;
                });
                setMenuById(map);
            })
            .catch((e) => setError(e.message));
    }, [orderId]);

    if (error) {
        return (
            <div style={{ padding: '1rem' }}>
                Failed to load order: {error}
            </div>
        );
    }

    if (!order) {
        return (
            <div style={{ padding: '1rem' }}>
                Loading order...
            </div>
        );
    }

    const itemsTotal = items.reduce(
        (sum, item) => sum + (item.price ?? 0),
        0
    );
    const total = itemsTotal + order.tax + order.tip;

    return (
        <div className='checkout-page'>
            <div className='checkout-card'>
                <h2>Order #{order.id}</h2>

                <section className='checkout-section'>
                    <p>Order Time: {new Date().toLocaleString()}</p>
                    <p>Status: {order.status}</p>
                </section>

                <section className='checkout-section'>
                    <h3>Items</h3>
                    {items.length === 0 ? (
                        <p>No items for this order</p>
                    ) : (
                        <ul className='checkout-items'>
                            {items.map((item) => {
                                const menu = item.itemid != null ? menuById[item.itemid] : undefined;
                                const name = menu ? menu.name : `Item #${item.itemid ?? 'unknown'}`;
                                const price = item.price ?? 0;
                                return (
                                    <li key={item.id} className='checkout-item'>
                                        {name} x - ${price.toFixed(2)}
                                        {item.firstName && ` - For: ${item.firstName}`}
                                        {item.notes && ` - Notes: ${item.notes}`}
                                    </li>
                                );
                            })}
                        </ul>
                    )}
                </section>

                <section className='checkout-section'>
                    <h3>Summary</h3>
                    <p className='summary-row'><span>Tax</span> <span>${order.tax.toFixed(2)}</span></p>
                    <p className="summary-row"><span>Tip</span> <span>${order.tip.toFixed(2)}</span></p>
                    <p className='summary-row total-row'>
                        <strong>Total</strong>
                        <strong>${total.toFixed(2)}</strong>
                    </p>
                </section>
            </div>
        </div>
    );
}