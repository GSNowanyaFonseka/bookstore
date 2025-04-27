/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.exception;



import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Hp
 */
@Provider
public class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException>{
    private static final Logger LOGGER = Logger.getLogger(OrderNotFoundExceptionMapper.class.getName());
    
    /**
     * converts a OrderNotFoundException into an HTTP Response
     * @param exception the exception that was thrown
     * @return response object with status 404 and JSON error details
     */
    @Override
    public Response toResponse(OrderNotFoundException exception){
        
        LOGGER.log(Level.WARNING,"Order not found: {0}", exception.getMessage());
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Order Not Found");
        errorResponse.put("message", exception.getMessage());
        
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
