/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.db.InMemoryDatabase;
import com.bookstore.bookstore.exception.AuthorNotFoundException;
import com.bookstore.bookstore.model.Author;
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
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    
    @GET
    public List<Author> getAllAuthors(){
        return database.getAllAuthors();
    }
    
    @GET
    @Path("/{id}")
    public Author getAuthor(@PathParam("id") Long id){
        Author author = database.getAuthorById(id);
        if(author == null){
                throw new AuthorNotFoundException("Author with ID " + id + " does not exist.");
        }
        
        return author;
    }
    
    @POST 
    public Response createAuthor(Author author){
        Author createdAuthor = database.addAuthor(author);
        return Response.status(Response.Status.CREATED)
                .entity(createdAuthor)
                .build();
    }
    
    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id")Long id, Author author){
        author.setId(id);
        Author updatedAuthor = database.updateAuthor(author);
        if(updatedAuthor == null){
            throw new AuthorNotFoundException("Author with ID " + id + " does not exist");
        }
        return updatedAuthor;
    }
    
    @DELETE
    @Path("/{id")
    public Response deleteAuthor(@PathParam("id") Long id){
        boolean deleted = database.deleteAuthor(id);
        if(!deleted){
            throw new AuthorNotFoundException("\"Author with ID \" + id + \" does not exist");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @GET
    @Path("/{id}/books")
    public List<Book> getAuthorBookd(@PathParam("id") Long id){
        if(!database.authorExists(id)){
            throw new AuthorNotFoundException("Author with ID \" + id + \" does not exist");
        }
        return database.getBooksByAuthorId(id);
    }
}
