/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Hp
 */
public class Cart {
    private Long customerId;
    private Map<Long, CartItem> items; // map of bookId to cartItem
    
    // default constructor
    public Cart(){
        this.items = new HashMap<>();
    }
    
    // constructor with customer ID
    public Cart(Long customerId){
        this.customerId = customerId;
        this.items = new HashMap<>();
    }
    
    // getters and setters
    public Long getCustomerId(){
        return customerId;
    }
    
    public void setCustomerId(Long customerId){
        this.customerId = customerId;
    }
    
    public Map<Long, CartItem> getItems(){
        return items;
    }
    
    public void setItems(Map<Long, CartItem> items){
        this.items = items;
    }
    
    // Add a book to the cart
    public void addItem(CartItem item){
        Long bookId = item.getBookId();
        
        // If book already exists in cart, update quantity
        if(items.containsKey(bookId)){
            CartItem existingItem = items.get(bookId);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        }else{
            items.put(bookId, item);
        }
    }
    
    // update item quantity
    public void updateItemQuantity(Long bookId, int quantity){
        if(items.containsKey(bookId)){
            CartItem item = items.get(bookId);
            item.setQuantity(quantity);
        }
    }
    
    // remove item from cart
    public void removeItem(Long bookId){
        items.remove(bookId);
    }
    
    // clear cart
    public void clear(){
        items.clear();
    }
    
    // calculate total price of all items in cart
    public double getTotalPrice(){
        double total = 0;
        for(CartItem item : items.values()){
            total+=item.getTotalPrice();
        }
        return total;
    }
    
    // get number of items in cart(total quantity)
    public int getTotalQuantity(){
        int total = 0;
        for(CartItem item : items.values()){
            total += item.getQuantity();
        }
        return total;
    }
   
}
