/**
 * Order Entity
 * Properties include:
 * - Id
 * -userid
 * -ordertime
 * -pickuptime
 * -area
 * -location
 * -tax
 * -tip
 * -pan
 * -expiryMonth
 * -expiryYear
 * -status
 */

package com.restaurant.entity;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="userid", nullable = false)
    private Long userid;

    @Column(name="ordertime")
    private LocalDateTime ordertime = LocalDateTime.now();

    @Column(name="pickuptime")
    private LocalDateTime pickuptime;

    @Column(name="area")
    private String area;

    @Column(name="location")
    private String location;

    @Column(name="tax", nullable = false)
    private Double tax = 0.0;

    @Column(name="tip")
    private Double tip = 0.0;

    @Column(name="pan")
    private String pan;

    @Column(name="expiryMonth")
    private Integer expiryMonth;

    @Column(name="expiryYear")
    private Integer expiryYear;

    @Column(name="status")
    private String status;


    // Getters and Setters
    
    public Long getUserid() {
        return userid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(LocalDateTime ordertime) {
        this.ordertime = ordertime;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

     public LocalDateTime getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(LocalDateTime pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   


    

}
