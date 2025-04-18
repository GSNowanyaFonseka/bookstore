/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.db.InMemoryDatabase;
import com.bookstore.bookstore.exception.BookNotFoundException;
import com.bookstore.bookstore.model.Book;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Hp
 */

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    
    @GET
    public List<Book> getAllBooks(){
        return database.getAllBooks();
    }
    
    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id")Long id){
       Book book = database.getBookId(id);
       if(book == null){
           throw new BookNotFoundException("Book with ID " + id + " does not exist");
       }
       return book;
    }
    
    @POST
    public Response createBook(Book book){
        Book createdBook = database.addBook(book);
        return Response.status(Response.Status.CREATED)
                .entity(createdBook)
                .build();
    }
    
    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") Long id, Book book){
        book.setId(id);
        Book updatedBook = database.updateBook(book);
        if(updatedBook == null){
            throw new BookNotFoundException("Book with ID " + id + " does not exist");
        }
        return updatedBook;
    }
    
    @DELETE 
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id){
        boolean deleted = database.deleteBook(id);
        if(!deleted){
            throw new BookNotFoundException("Book with ID " + id + " does not exist");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

