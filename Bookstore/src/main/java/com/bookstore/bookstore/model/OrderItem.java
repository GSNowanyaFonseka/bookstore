/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.model;

/**
 *
 * @author Hp
 */
public class OrderItem {
    private Long bookId;
    private String bookTitle;  // Store book title at time of order
    private int quantity;
    private double unitPrice;  // Store price at time of order

    // Default constructor
    public OrderItem() {
    }

    // Constructor with parameters
    public OrderItem(Long bookId, String bookTitle, int quantity, double unitPrice) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    // Constructor from CartItem
    public OrderItem(CartItem cartItem, String bookTitle) {
        this.bookId = cartItem.getBookId();
        this.bookTitle = bookTitle;
        this.quantity = cartItem.getQuantity();
        this.unitPrice = cartItem.getUnitPrice();
    }

    // Getters and setters
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    // Calculate total price for this item
    public double getTotalPrice() {
        return unitPrice * quantity;
    }
}
