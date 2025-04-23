/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.exception;

import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class BookNotFoundException extends RuntimeException{
    
    private static final Logger LOGGER = Logger.getLogger(BookNotFoundException.class.getName());
    
    /**
     * constructs a new BookNotFoundException with the specified detailed message
     * @param message the detailed message explaining the reason for the exception
     */
    public BookNotFoundException(String message){
        super(message);
        LOGGER.warning("BookNotFoundException thrown : " + message);
    }
}
