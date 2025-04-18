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

/**
 *
 * @author Hp
 */

@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException>{
    @Override
    public Response toResponse(BookNotFoundException exception){
     Map<String, String> errorResponse = new HashMap<>();
     errorResponse.put("error", "Book Not Found");
     errorResponse.put("message", exception.getMessage());
     
     return Response
             .status(Response.Status.NOT_FOUND)
             .entity(errorResponse)
             .type(MediaType.APPLICATION_JSON)
             .build();
    }
}
