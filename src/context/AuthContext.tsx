import React, { createContext, useContext, useState, ReactNode } from 'react';
import { login as authLogin } from '../api/auth';
import { setAuthToken } from '../api/client';
import { getUserByUsername, type User } from '../api/users';


interface AuthContextValue {
    user: User | null;
    token: string | null;
    login: (username: string, password:string) => Promise<void>;
    logout: () => void;
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode}) {
    const [user, setUser] = useState<User | null>(null);
    const [token, setToken] = useState<string | null>(null);

    const login = async (username: string, password: string) => {
        const {token} = await authLogin(username, password);
        setAuthToken(token);
        const user = await getUserByUsername(username);
        if (!user) {
            throw new Error('User not found');
        }
        setUser(user);
    };

    const logout = () => {
        setToken(null);
        setAuthToken(undefined);
        setUser(null);
    };

    const value: AuthContextValue = { user, token, login, logout };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
    const ctx = useContext(AuthContext);
    if(!ctx) throw new Error('useAuth must be used within a AuthProvider');
    return ctx;
}