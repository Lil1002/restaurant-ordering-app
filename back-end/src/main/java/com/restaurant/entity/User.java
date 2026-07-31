
/**
 * 
 * User Entity
 * Properties include:
 *  - id
 *  - username
 *  - password
 *  - first (users first name)
 *  - last (users last name)
 *  - phone 
 *  - email
 *  - imageurl
 *  - pan 
 *  - cc number 
 *  - expiryMonth
 *  - expiryYear
 *  - roles
 */ 

package com.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", nullable=false, unique=true)
    private String username;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="first", nullable = false)
    private String first;

    @Column(name="last", nullable = false)
    private String last;

    @Column(name="phone")
    private String phone;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="imageUrl")
    private String imageUrl;

    @Column(name="pan")
    private String pan;

    @Column(name="expiryMonth")
    private Integer expiryMonth;

    @Column(name="expiryYear")
    private Integer expiryYear;

    @Column(name="roles")
    private String roles = "USER";

    // Getters and Setters 

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    

    
}
