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
public class InvalidInputException extends RuntimeException{
    
    private static final Logger LOGGER = Logger.getLogger(InvalidInputException.class.getName());
    
    /**
     * constructs a new InvalidInputException with the given error message
     * @param message the detail message explaining why the exception occurred
     */
    public InvalidInputException(String message){
        super(message);
        LOGGER.warning("CartNotFoundException thrown: " + message);

    }
}
