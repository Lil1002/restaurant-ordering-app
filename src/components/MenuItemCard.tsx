import type { MenuItem } from '../api/menu';
import { buildFoodImageURL } from '../api/menu';

interface MenuItemCardProps {
    item: MenuItem;
    onAddToCart: (item: MenuItem) => void;
}

function MenuItemCard({item, onAddToCart}: MenuItemCardProps) {

    return (
        <div className='menu-item-card'>
            <img src={item.imageurl} alt={item.name} className='menu-item-image' />
            <div className='menu-item-body'>
                <h3>{item.name}</h3>
                <p>{item.description}</p>
            </div>
            <div className='menu-item-footer'>
                <span className='menu-item-price'>${item.price.toFixed(2)}</span>
                <button className='menu-item-add' onClick={() => onAddToCart(item)}>Add to Cart</button>
            </div>
        </div>
    );
}

export default MenuItemCard;