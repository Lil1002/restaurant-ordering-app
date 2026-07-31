/**
 * Item Entity
 * Properties Include:
 * -id
 * -orderid
 * -itemid
 * -price
 * -notes
 * -firstname
 */

package com.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;


@Entity
@Table(name="orderitems")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="orderid", nullable = false)
    private Long orderid;

    @Column(name="itemid", nullable = false)
    private Long itemid = 0L;

    @Column(name="price", nullable = false)
    private Double price;

    @Column(name="notes")
    private String notes;

    @Column(name="firstname")
    private String firstname;


    // Getters and Setters 

    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
}
