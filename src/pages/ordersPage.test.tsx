import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import OrdersPage from './OrdersPage';

import * as AuthContext from '../context/AuthContext';
import * as OrdersApi from '../api/orders';
import * as RouterDom from 'react-router-dom';

vi.mock('../context/AuthContext');
vi.mock('../api/orders');
vi.mock('react-router-dom');

describe('OrdersPage (beginner tests)', () => {
  const useAuthMock = AuthContext.useAuth as unknown as vi.Mock;
  const getOrdersMock = OrdersApi.getOrders as unknown as vi.Mock;
  const useNavigateMock = RouterDom.useNavigate as unknown as vi.Mock;

  let navigateMock: vi.Mock;

  beforeEach(() => {
    vi.clearAllMocks();
    navigateMock = vi.fn();
    useNavigateMock.mockReturnValue(navigateMock);
  });

  it('redirects to /login when user is not logged in', () => {
    useAuthMock.mockReturnValue({ user: null });

    render(<OrdersPage />);

    expect(navigateMock).toHaveBeenCalledWith('/login');
  });

  it("shows at least one order for the logged-in user", async () => {
    const user = { id: 1, username: 'testuser' };
    useAuthMock.mockReturnValue({ user });

    getOrdersMock.mockResolvedValue([
      {
        id: 101,
        area: '',
        location: '',
        ordertime: '',
        pickuptime: '',
        status: 'completed',
        tax: 5.33,
        tip: 1.23,
        userid: 1,       
        expiryMonth: 1,
        expiryYear: 2030,
        pan: '4111111111111111',
      },
    ]);

    render(<OrdersPage />);

    await waitFor(() => {
      expect(screen.getByText(/orders/i)).toBeInTheDocument();
    });

    const items = screen.getAllByRole('listitem');
    expect(items.length).toBeGreaterThan(0);
  });
});