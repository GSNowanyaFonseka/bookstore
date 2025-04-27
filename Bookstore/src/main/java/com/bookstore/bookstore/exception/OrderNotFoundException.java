/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.exception;

import java.util.logging.Logger;

/**
 * Custom exception to indicate that a Order was not found
 * 
 */
public class OrderNotFoundException extends RuntimeException{
    private static final Logger LOGGER = Logger.getLogger(OrderNotFoundExceptionMapper.class.getName());
    
    public OrderNotFoundException(String message){
        super(message);
        LOGGER.warning("OrderNotFoundException thrown: " + message);
    }
}
