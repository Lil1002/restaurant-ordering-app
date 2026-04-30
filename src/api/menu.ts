import { get } from './client';

export interface MenuItem {
    id: number;
    name: string;
    description: string;
    category: string;
    price: number;
    imageurl: string;
    available: boolean;
}

export function getMenuItems(): Promise<MenuItem[]> {
    return get<MenuItem[]>('/menuitems');
}

export interface ImagesResponse {
    food: string[];
    posters: string[];
}

export async function getImages(): Promise<ImagesResponse> {
    return get<ImagesResponse>('/images');
}

export function buildFoodImageURL(filename: string): string {
    return `/images/food/${filename}`;
}
