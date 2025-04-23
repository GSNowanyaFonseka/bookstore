/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.exception;

import java.util.logging.Logger;

/**
 *
 * Exception thrown when an author is not found in the system
 * 
 */
public class AuthorNotFoundException extends RuntimeException{
    
    private static final Logger LOGGER = Logger.getLogger(AuthorNotFoundException.class.getName());
    
    /**
     * Constructs a new AuthorNotFoundException with the specified details message
     * @param message the details message 
     */
    public AuthorNotFoundException(String message){
        super(message);
        LOGGER.warning("AuthorNotFoundException thrown: {0}" + message);
    }
}
