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
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException>{

    private static final Logger LOGGER = Logger.getLogger(OutOfStockExceptionMapper.class.getName());
    /**
     * converts an OutOfStockException to an HTTP response with 400 Bad request status
     * @param exception the OutOfStockException to be mapped to a response
     * @return a response with status BAD_REQUEST and a JSON message body containing the error details
     */
    @Override
    public Response toResponse(OutOfStockException exception) {
        
        LOGGER.log(Level.WARNING,"Handling OutOfStockException: {0}", exception.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Out of Stock");
        errorResponse.put("message", exception.getMessage());
        
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
