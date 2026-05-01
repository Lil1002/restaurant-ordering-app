// src/pages/OrderPage.test.tsx
import { describe, it, expect, vi } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import OrderPage from './OrderPage';

vi.mock('react-router-dom', () => ({
  useParams: () => ({ orderId: '1014' }),
}));

vi.mock('../api/menu', () => ({
  getMenuItems: () =>
    Promise.resolve([
      {
        id: 1,
        name: 'Bison Burger',
        description: '',
        category: 'entrees',
        price: 11.54,
        imageurl: '',
        available: true,
      },
    ]),
}));

vi.mock('../api/orders', () => ({
  getOrderById: () =>
    Promise.resolve({
      id: 1014,
      area: '',
      location: '',
      ordertime: '',
      pickuptime: '',
      status: 'In Progress',
      tax: 0,
      tip: 0,
      userid: 1,
      expiryMonth: 1,
      expiryYear: 2030,
      pan: '',
    }),
  getOrderItems: () =>
    Promise.resolve([
      { id: 1, orderid: 1014, itemid: 1, price: 11.54, firstName: null, notes: null },
    ]),
}));

describe('OrderPage', () => {
  it('shows order id and item name', async () => {
    render(<OrderPage />);

    await waitFor(() => {
      expect(screen.getByText(/order #1014/i)).toBeInTheDocument();
    });
    expect(screen.getByText(/bison burger/i)).toBeInTheDocument();
  });
});