import { get, post } from './client';


export interface RegisterPayload {
    username: string;
    password: string;
    first: string;
    last: string;
    phone: string;
    email: string;
    imageUrl: string;
    pan: string;
    expiryMonth: number;
    expiryYear: number;
    roles: string;
}

export interface User extends Omit<RegisterPayload, 'password'> {
    id:number;
}

export async function registerUser(payload: RegisterPayload): Promise<void> {
    await post<RegisterPayload, unknown>('/users', payload);
}

export async function getUserByUsername(username: string): Promise<User | null> {
    const users = await get<User[]>('/users');
    return users.find((u) => u.username === username) ?? null;
}