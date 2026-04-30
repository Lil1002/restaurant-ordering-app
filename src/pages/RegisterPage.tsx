import {useState} from 'react';
import { useNavigate } from 'react-router';
import { registerUser } from '../api/users';

export default function RegisterPage() {
    const [form,setForm] = useState({
        username: '',
        password: '',
        first: '',
        last: '',
        phone: '',
        email: '',
        imageUrl: '',
        pan: '',
        expiryMonth: '',
        expiryYear: '',
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();

    const handleChange =
    (field: keyof typeof form) =>
    (e: React.ChangeEvent<HTMLInputElement>) => {
        setForm({ ...form, [field]: e.target.value});
    };

    const handleSubmit = async () => {
        setError(null);
        setSuccess(false);
        setLoading(false);
        try {
            await registerUser({
                username: form.username,
                password: form.password,
                first: form.first,
                last: form.last,
                phone: form.phone,
                email: form.email,
                imageUrl: form.imageUrl,
                pan: form.pan,
                expiryMonth: Number(form.expiryMonth),
                expiryYear: Number(form.expiryYear),
                roles: 'USER',
            });
            setSuccess(true);
            navigate('/login');
        } catch (err) {
            console.error(err);
            setError('Registration failed. Please check your inputs')
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className='checkout-page'>
            <div className='checkout-card auth-card'>
            <h2>Register</h2>
            <form className='auth-form'
            onSubmit={(e) => {
                e.preventDefault();
                handleSubmit();
            }}
            >
                <div className='auth-field'>
                    <label className='auth-label'>
                        Username 
                        <input
                        className='auth-input'
                        value={form.username}
                        onChange={handleChange('username')}
                        autoComplete='username'
                        />
                    </label>
                </div>
                    <div className='auth-field'>
                        <label className='auth-label'>
                            Password 
                            <input
                            className='auth-input'
                            type="password"
                            value={form.password}
                            onChange={handleChange('password')}
                            autoComplete='new-password'
                            />
                        </label>
                    </div>
                    <div className='auth-field'>
                        <label className='auth-label'>
                            First Name 
                            <input
                            className='auth-input'
                            value={form.first}
                            onChange={handleChange('first')}
                            />
                        </label>
                    </div>
                    <div className='auth-field'>
                        <label className='auth-label'>
                            Last Name 
                            <input
                            className='auth-input'
                            value={form.last}
                            onChange={handleChange('last')}
                            />
                        </label>
                    </div>
                    <div className='auth-field'>
                        <label className='auth-label'>
                            Card Number 
                            <input 
                            className='auth-input'
                            value={form.pan} onChange={handleChange('pan')} />
                        </label>
                    </div>
                    <div className='auth-field'>
                        <label className='auth-label'>
                            Expiry Month (MM)
                            <input
                            className='auth-input'
                            value={form.expiryMonth}
                            onChange={handleChange('expiryMonth')}
                            />
                        </label>
                    </div>
                    <div className='auth-field'>
                        <label className='auth-label'>
                            Expiry Year 
                            <input 
                            className='auth-input'
                            value={form.expiryYear}
                            onChange={handleChange('expiryYear')}
                            />
                        </label>
                    </div>
                    {error && <p className='auth-error' style={{ color: 'red'}}>{error}</p>}
                    {success && (
                        <p className='auth-success'>
                            Registration successful. Redirecting to Login...
                        </p>
                    )}
                    <button className='register-button' type='submit' disabled={loading}>
                        {loading ? 'Registering...' : 'Register'}
                    </button>
            </form>
            </div>
        </div>
    );
}