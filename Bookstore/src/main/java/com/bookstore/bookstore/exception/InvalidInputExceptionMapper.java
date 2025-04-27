/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Hp
 */
@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException>{

    private static final Logger LOGGER = Logger.getLogger(InvalidInputExceptionMapper.class.getName());
    
    /**
     * converts an InvalidInputException into an HTTP Response
     * @param exception exception that was thrown
     * @return a Response object with status 400 and JSON error details
     */
    @Override
    public Response toResponse(InvalidInputException exception) {
        
        LOGGER.log(Level.WARNING, "Handling InvalidInputException: {0}", exception.getMessage());
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error","Invalid Input");
        errorResponse.put("message", exception.getMessage());
        
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
