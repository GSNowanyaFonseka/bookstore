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
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException>{
    
    private static final Logger LOGGER = Logger.getLogger(BookNotFoundExceptionMapper.class.getName());
    /**
     * converts a BookNotFoundException into an HTTP response
     * @param exception the thrown BookNotFoundException
     * @return a Response a object with HTTP 404 status and JSON error message
     */
    @Override
    public Response toResponse(BookNotFoundException exception){
        
     LOGGER.log(Level.WARNING, "Handling BookNotFoundException: {0}", exception.getMessage());
     // create the error response
     Map<String, String> errorResponse = new HashMap<>();
     errorResponse.put("error", "Book Not Found");
     errorResponse.put("message", exception.getMessage());
     
     // return the JSON response
     return Response
             .status(Response.Status.NOT_FOUND)
             .entity(errorResponse)
             .type(MediaType.APPLICATION_JSON)
             .build();
    }
}
