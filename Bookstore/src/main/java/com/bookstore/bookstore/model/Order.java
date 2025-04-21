/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hp
 */
public class Order {
    private Long id;
    private Long customerId;
    private List<OrderItem> items;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status; // "PLACED", "PROCESSING", "SHIPPED", "DELIVERED", etc.

    // Default constructor
    public Order() {
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = "PLACED";
    }

    // Constructor with parameters
    public Order(Long id, Long customerId) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = "PLACED";
        this.totalAmount = 0;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        calculateTotalAmount();
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    // Add an order item
    public void addItem(OrderItem item) {
        this.items.add(item);
        calculateTotalAmount();
    }
    
    // Calculate total amount
    private void calculateTotalAmount() {
        this.totalAmount = 0;
        for (OrderItem item : items) {
            this.totalAmount += item.getTotalPrice();
        }
    }
}
