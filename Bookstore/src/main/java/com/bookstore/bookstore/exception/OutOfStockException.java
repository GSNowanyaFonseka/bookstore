/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class OutOfStockException extends RuntimeException{
    
    private static final Logger LOGGER = Logger.getLogger(OutOfStockException.class.getName());
    /**
     * construct a new OutOfStockException with the given error message
     * @param message the detail message explain the exception occurred
     */
    public OutOfStockException(String message){
        super(message);
        LOGGER.log(Level.WARNING,"Out of stock exception: {0}", message);
    }
}
