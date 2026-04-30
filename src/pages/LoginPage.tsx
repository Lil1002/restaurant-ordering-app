import { useState} from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth} from '../context/AuthContext';


export default function LoginPage() {
    const { login } = useAuth();
    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async () => {
        setError(null);
        setLoading(true);
        try{
            await login(username, password);
            navigate('/home');
        } catch (err) {
            console.error(err);
            setError('Login failed. Check username/password.');
        }finally {
            setLoading(false);
        }
    };

    return (
        <div className='checkout-page'>
            <div className='checkout-card auth-card'>
            <h2>Login</h2>
            <form className='auth-form' onSubmit={(e) =>{
                e.preventDefault();
                handleSubmit();
                }}>
                <div className='auth-field'>
                    <label className='auth-label'>
                        Username
                        <input
                        className='auth-input'
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        autoComplete='username'
                        />
                    </label>
                </div>
                <div className='auth-field'>
                    <label className='auth-label'>
                        Password 
                        <input
                        className='auth-input'
                        type='password'
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        autoComplete='current-password'
                        />
                    </label>
                </div>
                {error && <p className='auth-error'>{error}</p>}
                <button type='submit' disabled={loading} className='login-button'>
                    {loading ? 'Logging in...' : 'Login'}
                </button>
            </form>
        </div>
        </div>
    );
}