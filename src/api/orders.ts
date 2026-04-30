import { get, post } from './client';


export interface NewOrder {
    area?: string,
    location?: string;
    tax: number;
    tip: number;
    userid: number;
    expiryMonth: number;
    expiryYear: number;
    pan: string;
}

export interface Order extends NewOrder  {
    id: number;
    ordertime: string | number;
    pickuptime: string | number;
    status: string;
}

export interface OrderItem {
    id: number;
    orderid: number;
    itemid: number | null;
    price: number;
    firstName?: string | null;
    notes?: string | null;
    
}

export async function createOrder(order: NewOrder): Promise<Order> {
    return post<NewOrder, Order>('/orders', order);
}

export async function getOrders(): Promise<Order[]> {
    const data = await get<any[]>('/orders');
    return data.map((o) => ({
        id: o.id,
        area: o.area,
        location: o.area,
        ordertime: o.ordertime,
        pickuptime: o.pickuptime,
        status: o.status,
        tax: o.tax,
        tip: o.tip,
        userid: o.userid,
        expiryMonth: o.expiryMonth,
        expiryYear: o.expiryYear,
        pan: o.pan,
    }));
}

export async function getOrderById(id: number): Promise<Order> {
    const o = await get<any>(`/orders/${id}`);
    return {
        id: o.id,
        area: o.area,
        location: o.area,
        ordertime: o.ordertime,
        pickuptime: o.pickuptime,
        status: o.status,
        tax: o.tax,
        tip: o.tip,
        userid: o.userid,
        expiryMonth: o.expiryMonth,
        expiryYear: o.expiryYear,
        pan: o.pan,
};
}

export async function getOrderItems(orderId: number): Promise<OrderItem[]> {
    return get<OrderItem[]>(`/items/order/${orderId}`);
}

export async function createOrderItems(orderId: number, items: Omit<OrderItem, 'id' | 'orderid'>[],): Promise<OrderItem[]> {
    const payload = items.map((item) => ({
        ...item,
        orderid: orderId,
    }));
    return post<typeof payload, OrderItem[]>(`/items/order/${orderId}`, payload);
}