import { describe, it, expect, vi } from 'vitest';
import { render, screen } from '@testing-library/react';
import type { MenuItem } from '../api/menu';

vi.mock('../api/menu', () => ({
  getMenuItems: () =>
    Promise.resolve<MenuItem[]>([
      {
        id: 1,
        name: 'Bison Burger',
        description: '',
        category: '',
        price: 10,
        imageurl: '',
        available: true,
      },
    ]),
}));

vi.mock('../context/CartContext', () => ({
  useCart: () => ({ addItem: () => {} }),
}));

vi.mock('../context/AuthContext', () => ({
  useAuth: () => ({ user: null }),
}));

// simple dummy card
vi.mock('../components/MenuItemCard', () => ({
  default: ({ item }: { item: MenuItem }) => <div>{item.name}</div>,
}));

import HomePage from './HomePage';

describe('HomePage', () => {
  it('shows a menu item name', async () => {
    render(<HomePage />);
    expect(await screen.findByText('Bison Burger')).toBeInTheDocument();
  });
});