/**
 * Menu Item Controller Tests
 * 
 * - testing retieving all menu items
 * 
 * - testing get menu item by id 
 * 
 * - testing get menu item by id not found
 * 
 * - testing create new menu item
 * 
 * - testing updating menu items
 */




package com.restaurant.controllers;


import com.restaurant.entity.MenuItem;
import com.restaurant.repository.MenuItemRepository;

import io.jsonwebtoken.lang.Arrays;

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
public class MenuItemControllerTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemController menuItemController;

    // GET all items
    @Test
    public void testGetAllMenuItems() {
        MenuItem item1 = new MenuItem();
        item1.setName("Nachos");
        MenuItem item2 = new MenuItem();
        item2.setName("Chicken Tenders");

        when(menuItemRepository.findAll()).thenReturn(List.of(item1, item2));

        List<MenuItem> result = menuItemController.getAllMenuItems();

        assertEquals(2, result.size());
        verify(menuItemRepository, times(1)).findAll();
    }

    //GET menu item by id 
    @Test
    public void testGetMenuItemByIdFound() {
        MenuItem item = new MenuItem();
        item.setName("Nachos");

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(item));

        ResponseEntity<?> response = menuItemController.getMenuItemById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(menuItemRepository, times(1)).findById(1L);
    }

    // GET menu item by id - not found 
    @Test
    public void testGetMenuItemByIdNotFound() {
        when(menuItemRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = menuItemController.getMenuItemById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // POST create new item 
    @Test
    public void testCreateMenuItem() {
        MenuItem item = new MenuItem();
        item.setName("Milk Shake");
        item.setPrice(10.99);
        item.setCategory("Drinks");

        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(item);

        ResponseEntity<?> response = menuItemController.createMenuItems(item);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }

    // PUT update menu item 
    @Test
    public void testUpdateMenuItem() {
        MenuItem existingMenuItem = new MenuItem();
        existingMenuItem.setName("Milk Shake");

        MenuItem updated = new MenuItem();
        updated.setName("Vanilla Milk Shake");
        updated.setPrice(11.99);

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(existingMenuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(existingMenuItem);

        ResponseEntity<?> response = menuItemController.updateMenuItem(1L, updated);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }
}
