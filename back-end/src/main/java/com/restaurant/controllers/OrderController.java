/**
 * Order Api Endpoints
 * --/orders
 * - (Get) all details
 * - (POST) add new order
 * 
 * -- /orders/{id}
 * - (GET) specific details
 * - (PUT) Change Details
 * - (DELETE) 
 * 
 * -- /orders/users/{userId}
 * - (GET) all orders for specific user 
 */

package com.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.entity.Order;
import com.restaurant.repository.OrderRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // Retrieve all order details
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Add new Order
    @PostMapping
    public ResponseEntity<?> addNewOrder(@RequestBody Order order) {
        try {
            Order newOrder = orderRepository.save(order);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred when creating a new order" + e.getMessage());
        }
    }

    // Get specific orders
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            Optional<Order> order = orderRepository.findById(id);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found" + id);
            }
        } catch(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred when finding the order" + e.getMessage());
        }
    }


    // Update orders 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        try {
            Optional<Order> existingOrder = orderRepository.findById(id);
            if (existingOrder.isPresent()) {
                Order order = existingOrder.get();
                order.setPickuptime(updatedOrder.getPickuptime());
                order.setArea(updatedOrder.getArea());
                order.setLocation(updatedOrder.getLocation());
                order.setTip(updatedOrder.getTip());
                order.setStatus(updatedOrder.getStatus());

                Order savedOrder = orderRepository.save(order);
                return ResponseEntity.ok(savedOrder);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found" + id);
            }
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred when updating the order" + e.getMessage());
        }
    }

    // Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id) {
        try {
            Optional<Order> findOrder = orderRepository.findById(id);
            if (findOrder.isPresent()) {
                orderRepository.deleteById(id);
                return ResponseEntity.ok("Order was successfully deleted" + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found" + id);
            }
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred when trying to delete order" + e.getMessage());
        }
    }

    // Get all orders for specific user
    @GetMapping("/user/{userid}")
    public ResponseEntity<?> getAllUserOrders(@PathVariable Long userid) {
        try {

            List<Order> orders = orderRepository.findByUserid(userid);

            if(orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orders not found " + userid);
            } 
            return ResponseEntity.ok(orders);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred when retrieving Orders" + e.getMessage());
        }
    }


}
    

