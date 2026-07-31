/**
 * Order Repository 
 * -Database helper
 */


package com.restaurant.repository;

import com.restaurant.entity.Order; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find by the Users Id
    List<Order> findByUserid(Long userid);
    
}
