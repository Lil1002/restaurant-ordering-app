import './App.css';
import { Route, Routes, Navigate, Link } from 'react-router-dom'
import HomePage from './pages/HomePage';
import CheckoutPage from './pages/CheckoutPage';
import LoginPage from './pages/LoginPage';
import OrdersPage from './pages/OrdersPage';
import Header from './components/Header';
import OrderPage from './pages/OrderPage';
import RegisterPage from './pages/RegisterPage';



function App() {
    return (
        <div className='app'>
            <Header />
            <Routes>
                <Route path='/' element={<Navigate to="/home" replace />} />
                <Route path="/home" element={<HomePage />} />
                <Route path='/checkout' element={<CheckoutPage />} />
                <Route path='/orders' element={<OrdersPage />} />
                <Route path='/orders/:orderId' element={<OrderPage />} />
                <Route path='/login' element={<LoginPage />} />
                <Route path='/register' element={<RegisterPage />} />
            </Routes>
        </div>
    );
}

export default App
