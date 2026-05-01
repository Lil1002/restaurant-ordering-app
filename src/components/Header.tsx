import { Link, useNavigate } from 'react-router-dom';
import {useCart} from '../context/CartContext';
import {useAuth } from '../context/AuthContext'

export default function Header() {
    const { totalItems } = useCart();
    const { user, logout }= useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/home');
    };

    return (
        <header className='header'>
            <div className='header-left'>
                <Link className='header-routes' to='/home'>Home</Link>
            </div>
            <div className='header-right'>
                <nav>
                    {user ? (
                        <>
                        <span className='header-text'>Hello, {user.username}</span>{' | '}
                        <button className='logout' type='button' onClick={handleLogout}> Logout</button>
                        </>
                    ) : (
                        <>
                        <Link className='header-routes' to='/login'>Login</Link> {' | '}
                        <Link className='header-routes' to='/register'>Register</Link>
                        </>
                    )}
                     {' | '}
                    <Link className='header-routes' to='/orders'>Orders</Link>
                    {' | '}
                     <Link className='header-routes' to='/checkout'>Cart ({totalItems})</Link>
                </nav>
            </div>
        </header>
    );
}