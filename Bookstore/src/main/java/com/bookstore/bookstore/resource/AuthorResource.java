/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.db.InMemoryDatabase;
import com.bookstore.bookstore.exception.AuthorNotFoundException;
import com.bookstore.bookstore.model.Author;
import com.bookstore.bookstore.model.Book;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * handles HTTP requests related to Authors
 * provides endpoints for CRUD operations and retrieving books by author.
 * @author Hp
 */
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    
    private static final Logger LOGGER = Logger.getLogger(AuthorResource.class.getName());
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    
    /**
     * retrieves a list of all authors
     * @return list of authors
     */
    @GET
    public List<Author> getAllAuthors(){
        LOGGER.info("Fetching all authors");
        return database.getAllAuthors();
    }
    
    /**
     * retrieves a specific author by ID
     * @param id of the author
     * @return AuthorNotFoundException if the author is not found
     */
    @GET
    @Path("/{id}")
    public Author getAuthor(@PathParam("id") Long id){
        LOGGER.log(Level.INFO,"Fetching author with ID: {0}", id);
        Author author = database.getAuthorById(id);
        if(author == null){
                LOGGER.log(Level.WARNING,"Author with ID {0} not found", id);
                throw new AuthorNotFoundException("Author with ID " + id + " does not exist.");
        }
        
        return author;
    }
    
    /**
     * creates a new author
     * @param author the author to create
     * @return HTTP response with the created author
     */
    @POST 
    public Response createAuthor(Author author){
        LOGGER.info("Creating a new author");
        Author createdAuthor = database.addAuthor(author);
        return Response.status(Response.Status.CREATED)
                .entity(createdAuthor)
                .build();
    }
    
    /**
     * updates an existing author
     * @param id the ID of the author to update
     * @param author the updated author data
     * @return the updates author
     */
    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id")Long id, Author author){
        LOGGER.log(Level.INFO,"Updating author with ID: {0}", id);
        author.setId(id);
        Author updatedAuthor = database.updateAuthor(author);
        if(updatedAuthor == null){
            LOGGER.log(Level.WARNING,"Author with ID {0} not found for update", id);
            throw new AuthorNotFoundException("Author with  " + id + " does not exist");
        }
        return updatedAuthor;
    }
    
    /**
     * deletes an author by ID
     * @param id the ID of the author to delete
     * @return HTTP response indication the result
     */
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id){
        LOGGER.log(Level.INFO,"Deleting author with ID: {0}", id);
        boolean deleted = database.deleteAuthor(id);
        if(!deleted){
            LOGGER.log(Level.WARNING,"Author with ID {0} not foundfor deletion", id);
            throw new AuthorNotFoundException("Author with ID " + id + " does not exist");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    /**
     * retrieves a list of books written by a specific author
     * @param id the ID of the author
     * @return list of books by the author
     */
    @GET
    @Path("/{id}/books")
    public List<Book> getAuthorBookd(@PathParam("id") Long id){
        LOGGER.log(Level.INFO,"Fetching books for author with ID: {0}", id);
        if(!database.authorExists(id)){
            LOGGER.log(Level.WARNING,"Author with ID {0} not found when fetching books", id);
            throw new AuthorNotFoundException("Author with ID " + id + " does not exist");
        }
        return database.getBooksByAuthorId(id);
    }
}
