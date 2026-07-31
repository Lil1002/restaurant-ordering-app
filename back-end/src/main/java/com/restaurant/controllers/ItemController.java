/** 
 * Item API Endpoints
 * -- /items
 * - (GET) all items from all orders
 * 
 * -- /items/{id}
 * - (GET) item by id
 * - (PUT) update item
 * - (DELETE) reomve from order
 * 
 * -- /items/order/{orderId}
 * -(GET) all items from specific order
 * - (POST) add item to order
 *  - (DELETE) item from order
 */

package com.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurant.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
import com.restaurant.entity.Item;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;






@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // Get all items
    @GetMapping
    public ResponseEntity<?> getAllItems () {
        try {
            List<Item> items = itemRepository.findAll();
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred when retrieving all items" + e.getMessage());
        }
    }
        
    // Get items by id 
    @GetMapping("/{id}")
    public  ResponseEntity<?> getItemById(@PathVariable Long id) {
        try {
            Optional<Item> item = itemRepository.findById(id);
            if (item.isPresent()) {
                return ResponseEntity.ok(item.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found" + id); 
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when retrieving items" + e.getMessage());
        }
    }


    // Update items by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        try {
            Optional<Item> existingItem = itemRepository.findById(id);
            if (existingItem.isPresent()) {
                Item item = existingItem.get();
                item.setNotes(updatedItem.getNotes());
                item.setFirstname(updatedItem.getFirstname());

                Item savedItem = itemRepository.save(item);
                return ResponseEntity.ok(savedItem);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found" + id);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when updating an item" + e.getMessage());
        }
    }


    // Delete item by id
    @DeleteMapping("/{id}") 
    public ResponseEntity<?> deleteItemById(@PathVariable Long id) {
        try {
            Optional<Item> findItem = itemRepository.findById(id);
            if (findItem.isPresent()) {
                itemRepository.deleteById(id);
                return ResponseEntity.ok("Order was deleted" + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found" + id);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred deleting this item" + e.getMessage());
        }
    }

    // Get all items from specific order
    @GetMapping("/order/{orderid}")
    public ResponseEntity<?> getItemsByOrderId(@PathVariable Long orderid) {
        try {
            List<Item> items = itemRepository.findAllByOrderid(orderid);
            if (items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Items not found" + orderid);
            }
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred retrieving items" + e.getMessage());
        }
    }

    // add item to order
    @PostMapping("/order/{orderid}")
    public ResponseEntity<?> addItem (@PathVariable Long orderid, @RequestBody List<Item> items) {
        try {
            for(Item item : items) {
                item.setOrderid(orderid);
            }

            List<Item> newItems = itemRepository.saveAll(items);
            return ResponseEntity.status(HttpStatus.CREATED).body(newItems);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred adding new items" + e.getMessage());
        }
    }

    // Delete all items in order 
    @DeleteMapping("/order/{orderid}")
    public ResponseEntity<?> deleteItemFromOrder(@PathVariable Long orderid) {
        try {
            List<Item> items = itemRepository.findAllByOrderid(orderid);
            if(!items.isEmpty()) {
                itemRepository.deleteAll(items);
                return ResponseEntity.ok("All items were deleted" + orderid);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items found in order" + orderid);
            }
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when deleting all items" + e.getMessage());
        }
    }
    
    
}
