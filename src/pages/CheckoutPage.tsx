import { useCart } from '../context/CartContext';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { createOrder, createOrderItems } from '../api/orders';


export default function CheckoutPage() {
    const { items, totalItems, clearCart } = useCart();
    const { user } = useAuth();
    const navigate = useNavigate();

    const subtotal = items.reduce(
        (sum, c) => sum + c.menuItem.price * c.quantity,
        0
    );

    const taxRate = 0.06;
    const tax = subtotal * taxRate;
    const [tip, setTip] = useState(0);
    const total = subtotal + tax + tip;

    if (!items.length) {
        return <div style={{ padding: '1rem' }}>Your cart is empty.</div>;
    }

    if (!user) {
        return <div style={{ padding: '1rem' }}>You must be logged in to checkout</div>;
    }

    const handlePlaceOrder = async () => {
        try {
            const order = await createOrder({
                tax,
                tip,
                userid: user.id,
                pan: user.pan,
                expiryMonth: user.expiryMonth,
                expiryYear: user.expiryMonth,
            });

            await createOrderItems(
                order.id,
                items.map((c) => ({
                    itemid: c.menuItem.id,
                    price: c.menuItem.price,
                    firstName: undefined,
                    notes: undefined,
                }))
            );

            clearCart();
            navigate(`/orders/${order.id}`);
        } catch (err) {
            console.error('Failed to place order', err);
            alert('Failed to place order. Check the console for details');
        }
    }

    return (
        <div className='checkout-page'>
            <div className='checkout-card'>
            <h2>Check out</h2>

            <section className='checkout-section'>
            <h3>Items ({totalItems})</h3>
            <ul className='checkout-items'>
                {items.map((c) => (
                    <li key={c.menuItem.id} className='checkout-item'>
                        {c.menuItem.name} x {c.quantity} - $
                        {(c.menuItem.price * c.quantity).toFixed(2)}
                    </li>
                ))}
            </ul>
            </section>

            <section className='checkout-section'>
            <h3>Summary</h3>
            <p className='summary-row'>
            <span>Subtotal: ${subtotal.toFixed(2)}</span>
            <span>Tax: ${tax.toFixed(2)}</span>
            </p>

            <p className='summary-row'>
                <span>
                Tip: 
                </span>
                <span>
                $
                <input
                    type='number'
                    id='tip-amount'
                    name='tip'
                    step='0.01'
                    min='0'
                    placeholder='0.00'
                    value={tip}
                    onChange={(e) => setTip(Number(e.target.value) || 0.00)}
                    style={{ width: '5rem' }}
                />
            </span>
            </p>
            <p className='summary-row total-row'>
                <strong>Total</strong>
                <strong>${total.toFixed(2)}</strong>
                </p>
            </section>

            <section className='checkout-section'>   
            <h3>Payment</h3>
            <p>
                Using card on file ending in{' '}
                {user.pan.slice(-4)} (exp {user.expiryMonth}/{user.expiryYear})
            </p>
            </section> 
            <button onClick={handlePlaceOrder} type='submit' className='checkout-button'>Place Order</button>
        </div>
        </div>
    );
}