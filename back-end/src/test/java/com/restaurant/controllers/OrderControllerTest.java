/**
 * Order Controller Tests
 * 
 * - testing retrieving all orders
 * 
 * - testing retrieving all orders by userid
 * 
 * - testing adding new order 
 */
package com.restaurant.controllers;


import com.restaurant.entity.Order;
import com.restaurant.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderController orderController;

    // GET all orders
    @Test
    public void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        List<Order> result = orderController.getAllOrders();

        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    // GET all orders by user id
    @Test 
    public void testGetOrdersByUserId() {
        Order order1 = new Order();
        order1.setUserid(1L);
        Order order2 = new Order();
        order2.setUserid(1L);

        when(orderRepository.findByUserid(1L)).thenReturn(List.of(order1, order2));

        ResponseEntity<?> response = orderController.getAllUserOrders(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderRepository, times(1)).findByUserid(1L);
    }

    // POST New order
    @Test
    public void testAddNewOrder() {
        Order order = new Order();
        order.setStatus("Placed");
        order.setTax(2.0);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        ResponseEntity<?> response = orderController.addNewOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
