/**
 * Menu Item API End Points
 * -- /api/menuitems
 * -(GET) to retrieve all food
 * - (POST) to add new menu items
 * 
 * -- /api/menuitems/{id}
 * - (GET) retrieve specific menu item
 * - (PUT) to change details
 * - (DELETE) remove menu items 
 */

package com.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import java.util.List;
import com.restaurant.entity.MenuItem;
import com.restaurant.repository.MenuItemRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/menuitems")
public class MenuItemController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Retrieve all Menu Items
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }
    
    // Create new Menu item
    @PostMapping
    public ResponseEntity<?> createMenuItems(@RequestBody MenuItem menuItem) {
        try{
            MenuItem savedMenuItem = menuItemRepository.save(menuItem);
            return new ResponseEntity<>(savedMenuItem, HttpStatus.CREATED);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error has occurred when creating a new menu item" + e.getMessage());
        }
    }

    // Get Menu Item by Id
    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuItemById(@PathVariable Long id) {
        try {
            Optional<MenuItem> menuItem = menuItemRepository.findById(id);
            if (menuItem.isPresent()) {
                return ResponseEntity.ok(menuItem.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu Item not found" + id);
            }
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when retrieving the menu item" + e.getMessage());
        }
    }

    // Update Menu Item by Id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updateMenuItem) {
        try{
            Optional<MenuItem> existingMenuItem = menuItemRepository.findById(id);
            if (existingMenuItem.isPresent()) {
                MenuItem menuItem = existingMenuItem.get();
                menuItem.setName(updateMenuItem.getName());
                menuItem.setCategory(updateMenuItem.getCategory());
                menuItem.setDescription(updateMenuItem.getDescription());
                menuItem.setPrice(updateMenuItem.getPrice());
                menuItem.setImageurl(updateMenuItem.getImageurl());
                menuItem.setAvailable(updateMenuItem.getAvailable());

                MenuItem savedMenuItem = menuItemRepository.save(menuItem);
                return ResponseEntity.ok(savedMenuItem);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu Item not found" + id);
            }
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred when updating the menu item" + e.getMessage());
        }
    }
    

    // Delete Menu Item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItemById(@PathVariable Long id) {
        try {
            Optional<MenuItem> findMenuItem = menuItemRepository.findById(id);
            if(findMenuItem.isPresent()) {
                menuItemRepository.deleteById(id);
                return ResponseEntity.ok("Menu Item was successfully deleted" + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu Item not found" + id);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when deleting the menu item" + e.getMessage());
        }
    }
    

}
