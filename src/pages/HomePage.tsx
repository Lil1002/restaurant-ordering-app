import { useEffect, useState } from 'react';
import MenuItemCard from '../components/MenuItemCard';
import type { MenuItem } from '../api/menu';
import { getMenuItems } from '../api/menu';
import { useCart } from '../context/CartContext';

export default function HomePage() {
    const [items, setItems] = useState<MenuItem[]>([]);
    const { addItem } = useCart();

    useEffect(() => {
        getMenuItems()
            .then((data) => {
                setItems(
                    data.map((m)=> ({
                        id: m.id,
                        name: m.name,
                        description: m.description,
                        category: m.category,
                        price: m.price,
                        imageurl: m.imageurl,
                        available: m.available,
                    }))
                );
            })
            .catch((e) => {
                console.error('Failed to load menu data', e);
            });
    }, []);


    const handleAddToCart = (item: MenuItem) => {
        addItem(item);
    }

    return (
        <div className='menu-grid'>
            {items.map((item) => (
                <MenuItemCard
                    key={item.id}
                    item={item}
                    onAddToCart={handleAddToCart}
                />
            ))}
        </div>
    );
}