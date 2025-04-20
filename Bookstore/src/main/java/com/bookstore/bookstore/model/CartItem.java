/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.model;

/**
 *
 * @author Hp
 */
public class CartItem {
    private Long bookId;
    private int quantity;
    private double unitPrice;  // Storing the price at the time it was added to cart

    // Default constructor
    public CartItem() {
    }

    // Constructor with parameters
    public CartItem(Long bookId, int quantity, double unitPrice) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and setters
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
