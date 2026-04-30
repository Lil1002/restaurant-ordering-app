export interface LoginResult {
    token: string;
}

function buildBasicAuthHeader(username: string, password: string): string {
    const value = btoa(`${username}:${password}`);
    return `Basic ${value}`;
}

export async function login(username: string, password: string): Promise<LoginResult> {
    const res = await fetch('/auth/login', {
        method: 'POST',
        headers: {
            Authorization: buildBasicAuthHeader(username,password),
        },
        
    }); console.log('login status:', res.status);

    if (!res.ok) {
        throw new Error(`Login failed: ${res.status}`);
    }

    const token = await res.text();
    console.log('raw token from login', token);
    return { token };
}