import { describe, it, expect, vi } from 'vitest';
import { render, screen } from '@testing-library/react';
import CheckoutPage from './CheckoutPage';

vi.mock('../context/CartContext', () => ({
  useCart: () => ({
    items: [],
    totalItems: 0,
    clearCart: () => {},
  }),
}));

vi.mock('../context/AuthContext', () => ({
  useAuth: () => ({
    user: { id: 1, pan: '4111111111111111', expiryMonth: 12, expiryYear: 2030 },
  }),
}));

vi.mock('../api/orders', () => ({
  createOrder: () => Promise.resolve({ id: 1 }),
  createOrderItems: () => Promise.resolve([]),
}));

vi.mock('react-router-dom', () => ({
  useNavigate: () => () => {},
}));

describe('CheckoutPage', () => {
  it('shows empty cart message', () => {
    render(<CheckoutPage />);
    expect(screen.getByText(/your cart is empty/i)).toBeInTheDocument();
  });
});