/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.db.InMemoryDatabase;
import com.bookstore.bookstore.exception.AuthorNotFoundException;
import com.bookstore.bookstore.exception.BookNotFoundException;
import com.bookstore.bookstore.exception.InvalidInputException;
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
import java.util.logging.Logger;

/**
 * managing book entities through RESTful APIs
 * provide endpoints to perform CRUD operations
 * @author Hp
 */

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    
    private static final Logger LOGGER = Logger.getLogger(BookResource.class.getName());
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    
    /**
     * retrieves all books from the database
     * @return list of all books
     */
    @GET
    public List<Book> getAllBooks(){
        LOGGER.info("Fetching all books");
        return database.getAllBooks();
    }
    
    /**
     * retrieves a book by its ID
     * @param id of the book to retrieve
     * @return book object
     */
    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id")Long id){
       LOGGER.info("Fetching book with ID: "+ id);
       Book book = database.getBookId(id);
       if(book == null){
           throw new BookNotFoundException("Book with ID " + id + " does not exist");
       }
       return book;
    }
    
    /**
     * creates a new book with the author 
     * @param book object to create
     * @return HTTP Response with created book
     */
    @POST
    public Response createBook(Book book){
    //        Book createdBook = database.addBook(book);
    //        return Response.status(Response.Status.CREATED)
    //                .entity(createdBook)
    //                .build();

    LOGGER.info("Creating a new book : " + book.getTitle());
    // check if author exists
    if(book.getAuthorId() != null && !database.authorExists(book.getAuthorId())){
        LOGGER.warning("Author with ID " + book.getAuthorId() + " not found during book creation");
        throw new AuthorNotFoundException("Author with ID " + book.getAuthorId() + " does not exist");
    }
    
    if(book.getPublicationYear() <= 0 || book.getPrice() <= 0 || book.getStock() <= 0){
        LOGGER.warning("Invalid input detected while creating book");
        throw new InvalidInputException("Numeric values must be positive");
    }
    
    if(book.getAuthorId() <= 0 ){
        LOGGER.warning("Invalid author ID during book creation");
        throw new InvalidInputException("Author ID can't be negative");
    }
    
    Book createdBook = database.addBook(book);
    
    // Add book ID to author's book List
    if(book.getAuthorId() != null){
        Author author = database.getAuthorById(book.getAuthorId());
        if(author != null){
            author.addBookId(createdBook.getId());
            database.updateAuthor(author);
            LOGGER.info("Associated new book with author ID: " + book.getAuthorId());
        }
    }
    
    return Response.status(Response.Status.CREATED)
            .entity(createdBook)
            .build();
    }
    
    /**
     * updates an existing book with new details
     * @param id ID of the book to update
     * @param book the updated Book object
     * @return updated Book object
     */
    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") Long id, Book book){
        //        book.setId(id);
        //        Book updatedBook = database.updateBook(book);
        //        if(updatedBook == null){
        //            throw new BookNotFoundException("Book with ID " + id + " does not exist");
        //        }
        //        return updatedBook;

        LOGGER.warning("Book with ID : " + id + " not found for update");
        
        // check if book exists
        Book existingBook = database.getBookId(id);
        if(existingBook == null){
            LOGGER.warning("Updating book with ID: " + id + "not found for update");
            throw new BookNotFoundException("Book with ID " + id + " does not exist");
        }

        // check if author exists
        if(book.getAuthorId() != null && !database.authorExists(book.getAuthorId())){
            LOGGER.warning("Author with ID " + book.getAuthorId() + " not found during book update");
            throw new AuthorNotFoundException("Author with ID " + book.getAuthorId() + " does not exist");
        }

        // update author references if author changed
        if(existingBook.getAuthorId() != null && !existingBook.getAuthorId().equals(book.getAuthorId())){
            // remove book from old author
            Author oldAuthor = database.getAuthorById(existingBook.getAuthorId());
            if(oldAuthor != null){
                oldAuthor.removeBookId(id);
                database.updateAuthor(oldAuthor);
                LOGGER.info("Removed book ID " + id + " from old author ID " + existingBook.getAuthorId());

            }

            // add book to new author
            if(book.getAuthorId() != null){
                Author newAuthor = database.getAuthorById(book.getAuthorId());
                if(newAuthor != null){
                    newAuthor.addBookId(id);
                    database.updateAuthor(newAuthor);
                    LOGGER.info("Added book ID " + id + " to new author ID " + book.getAuthorId());
                }
            } 
        }
        book.setId(id);
            return database.updateBook(book);
    }

    /**
     * deletes a book by its ID
     * @param id of the book to delete
     * @return HTTP Response with no content
     */
    @DELETE 
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id){
        LOGGER.info("Deleting book with ID: " + id);
        boolean deleted = database.deleteBook(id);
        if(!deleted){
            LOGGER.warning("Book with ID " + id + " not found dor deletion");
            throw new BookNotFoundException("Book with ID " + id + " does not exist");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

