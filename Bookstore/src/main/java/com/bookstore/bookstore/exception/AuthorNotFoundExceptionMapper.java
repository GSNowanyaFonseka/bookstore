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
 *Exception to handle AuthorNotFoundException and return a JSON response
 * @author Hp
 */
@Provider
public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException>{

    private static final Logger LOGGER = Logger.getLogger(AuthorNotFoundExceptionMapper.class.getName());
    
    /**
     * converts an AuthorNotFoundException into an HTTP response
     * @param exception the thrown AuthorNotFoundException
     * @return response object with 404 status and JSON error message
     */
    @Override
    public Response toResponse(AuthorNotFoundException exception) {
        
        LOGGER.log(Level.WARNING, "Handle AuthorNotFoundExceptio: {0}" , exception.getMessage());
        // create a map to hold the error details
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Author NotFound");
        errorResponse.put("message", exception.getMessage());
        
        // build and return the HTTP response with status 404 (NOT_FOUND) and JSON body
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
