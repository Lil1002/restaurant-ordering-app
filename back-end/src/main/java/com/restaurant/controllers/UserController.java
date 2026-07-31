/**
 * API Endpoint for Users 
 * -- /api/users
 * - (Get) Retrieve all details
 * - (POST) add new user
 * 
 * -- /api/users/{id}
 * -(GET) Retrieve specific details
 * - (PUT) to change details
 * - (DELETE) remove user
 */

package com.restaurant.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.restaurant.repository.UserRepository;
import com.restaurant.entity.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.restaurant.config.SecurityConfig;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Retrieve all users 
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Create new user 
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
      try {

            if (userRepository.existsByUsername(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user.setRoles("USER");

            User saved = userRepository.save(user);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred when registering " + e.getMessage());
        }
    }

    // Get User by Id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userRepository.findById(id);

            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found" + id);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error occurred when retriving use: " + e.getMessage());
        }
    }

    // Update User details 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updateUser) {
        try {
            Optional<User> existingUser = userRepository.findById(id);
            if (existingUser.isPresent()){
                User user = existingUser.get();
                user.setFirst(updateUser.getFirst());
                user.setLast(updateUser.getLast());
                user.setUsername(updateUser.getUsername());
                user.setEmail(updateUser.getEmail());
                user.setPassword(updateUser.getPassword());
                user.setImageUrl(updateUser.getImageUrl());
                user.setPhone(updateUser.getPhone());
                user.setPan(updateUser.getPan());
                user.setExpiryMonth(updateUser.getExpiryMonth());
                user.setExpiryYear(updateUser.getExpiryYear());

                User savedUser = userRepository.save(user);
                return ResponseEntity.ok(savedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + id);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occurred when trying to update user details" + e.getMessage());
        }
    }
    
    // Delete User by id 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> findUser = userRepository.findById(id);
            if (findUser.isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("User was successfully deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User can not be found: " + id);
            }
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when trying to delete user" + e.getMessage());
        }
    }

}
    
