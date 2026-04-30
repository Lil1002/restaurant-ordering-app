const API_BASE_URL = '/api';

let authToken: string | null = null;

export function setAuthToken(token?:string) {
    authToken = token ?? null;
}

function authHeaders() {
    return authToken ? { Authorization: `Bearer ${authToken}` } : {};
}

export async function get<T>(path: string, options: RequestInit = {}): Promise<T> {
    const res = await fetch(`${API_BASE_URL}${path}`, {
        ...options,
        method: 'GET',
        headers: {
            ...(options.headers || {}),
            ...authHeaders(),
        },
    });

    if (!res.ok) {
        throw new Error(`GET ${path} failed: ${res.status} ${res.statusText}`);
    }

    return res.json() as Promise<T>;
}

export async function post<TBody, TResponse>(
    path: string,
    body: TBody,
    options: RequestInit = {}
): Promise<TResponse> {
    const res = await fetch(`${API_BASE_URL}${path}`, {
        ...options,
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...(options.headers || {}),
            ...authHeaders(),
        },
        body: JSON.stringify(body),
    });

    if (!res.ok) {
        throw new Error(`POST ${path} failed: ${res.status} ${res.statusText}`);
    }

    return res.json() as Promise<TResponse>
}