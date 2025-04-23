/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
@Provider
public class CartNotFoundExceptionMapper implements ExceptionMapper<CartNotFoundException>{

    private static final Logger LOGGER = Logger.getLogger(CartNotFoundExceptionMapper.class.getName());
    
    /** 
     * converts a CartNotFoundException into a HTTP response
     * @param exception the thrown CartNotFoundException
     * @return a Response object with HTTP 404 status and JSON error message
     */
    
    @Override
    public Response toResponse(CartNotFoundException exception) {
        
        LOGGER.log(Level.WARNING,"Handling CartNotFoundException: {0}", exception.getMessage());
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Cart Not Found");
        errorResponse.put("message", exception.getMessage());
        
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
