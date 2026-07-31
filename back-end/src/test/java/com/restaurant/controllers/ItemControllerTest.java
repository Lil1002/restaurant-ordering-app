/** 
 * Item Controller Tests
 * 
 * - testing retieving items by order id
 * 
 * - testing retrieving items by order id - not found
 */
package com.restaurant.controllers;


import com.restaurant.entity.Item;
import com.restaurant.repository.ItemRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock 
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemController itemController;

    // GET items by order id
    @Test
    public void testGetItemsByOrderId() {
        Item item1 = new Item();
        item1.setOrderid(1L);
        Item item2 = new Item();
        item2.setOrderid(1L);

        when(itemRepository.findAllByOrderid(1L)).thenReturn(List.of(item1, item2));

        ResponseEntity<?> response = itemController.getItemsByOrderId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(itemRepository, times(1)).findAllByOrderid(1L);
    }

    // GET items by order id - not found 
    @Test
    public void testGetItemsByOrderIdNotFound() {
        when(itemRepository.findAllByOrderid(99L)).thenReturn(List.of());

        ResponseEntity<?> response = itemController.getItemsByOrderId(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
}
