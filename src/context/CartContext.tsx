import React, { createContext, useContext, useState, ReactNode } from 'react';
import type { MenuItem } from '../api/menu';

export interface CartItem {
    menuItem: MenuItem;
    quantity: number;
}

interface CartContextValue {
    items: CartItem[];
    addItem: (item: MenuItem) => void;
    removeItem: (menuItemId: number) => void;
    clearCart: () => void;
    totalItems: number;
}

const cartContext = createContext<CartContextValue | undefined>(undefined);

export function CartProvider({children}: { children: ReactNode}) {
    const [items, setItems] = useState<CartItem[]>([]);

    const addItem = (menuItem: MenuItem) => {
        setItems((prev) => {
            const existing = prev.find((c) => c.menuItem.id === menuItem.id);
            if (existing) {
                return prev.map((c) => 
                c.menuItem.id === menuItem.id 
                ? { ... c, quantity: c.quantity + 1} 
                : c);
            }
            return [...prev, { menuItem, quantity: 1}];
        });
    };

    const removeItem = (menuItemId: number) => {
        setItems((prev) => prev.filter((c) => c.menuItem.id !== menutItemId));
    };

    const clearCart = () => setItems([]);

    const totalItems = items.reduce((sum, c) => sum + c.quantity, 0);

    const value: CartContextValue = {
        items,
        addItem,
        removeItem,
        clearCart,
        totalItems,
    };

    return <cartContext.Provider value={value}>{children}</cartContext.Provider>;
}

export function useCart() {
    const ctx = useContext(cartContext);
    if(!ctx) {
        throw new Error('useChart must be used within a CartProvider');
    }
    return ctx;
}