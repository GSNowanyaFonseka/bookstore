/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.exception;

import java.util.logging.Logger;

/**
 *
 * Custom exception to indicate that a Customer was not found
 */
public class CustomerNotFoundException extends RuntimeException{
    
    private static final Logger LOGGER = Logger.getLogger(CustomerNotFoundExceptionMapper.class.getName());
    
    public CustomerNotFoundException(String message){
        super(message);
        LOGGER.warning("CustomerNotFoundException thrown: " + message);
    }
}
