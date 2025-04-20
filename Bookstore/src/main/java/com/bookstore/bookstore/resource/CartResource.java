/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.db.InMemoryDatabase;
import com.bookstore.bookstore.exception.BookNotFoundException;
import com.bookstore.bookstore.exception.CustomerNotFoundException;
import com.bookstore.bookstore.exception.OutOfStockException;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Cart;
import com.bookstore.bookstore.model.CartItem;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Hp
 */
@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    
    @GET
    public Cart getCart(@PathParam("customerId") Long customerId){
        // check if customer exists
        if(!database.customerExists(customerId)){
            throw new CustomerNotFoundException("Customer with ID " + customerId + "does not exist");
        }
        
        // get or create cart
        Cart cart = database.getCartByCustomerId(customerId);
        if(cart == null){
            cart = database.createCart(customerId);
        }
        
        return cart;
    }
    
    @POST 
    @Path("/items")
    public Cart addToCart(@PathParam("customerId") Long customerId, CartItem item){
        // check if customer exists
        if(!database.customerExists(customerId)){
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exists");
        }
        
        // check if book exists
        Book book = database.getBookId(item.getBookId());
        if(book == null){
            throw new BookNotFoundException("Book with ID " + item.getBookId() + " does not exists");
        }
        
        // check quantity
        if(item.getQuantity() <=0 ){
            throw new OutOfStockException("Quantity must be greater than zero");
        }
        
        //Add to cart
        Cart updatedCart = database.addToCart(customerId,item);
        if(updatedCart == null){
            throw new OutOfStockException("Not enough stock available for book with ID " + item.getBookId());
        }
        
        return updatedCart;
    }
    
    @PUT
    @Path("/items/{bookId}")
    public Cart updateCartItem(
                    @PathParam("customerId") Long customerId,
                    @PathParam("bookId")Long bookId,
                    @QueryParam("quantity") int quantity){
        
        // check if customer exists
        if(!database.customerExists(customerId)){
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist");
        }
        
        // check if cart exists
        Cart cart = database.getCartByCustomerId(customerId);
        if(cart == null){
            throw new BookNotFoundException("Cart for customer with ID " + customerId + " does not exist");
        }
        
        // check if book exists
        Book book = database.getBookId(bookId);
        if(book == null){
            throw new BookNotFoundException("Book with ID " + bookId + " does not exist");
        }
        
        // check if book is in cart
        if(!cart.getItems().containsKey(bookId)){
            throw new BookNotFoundException("Book with ID " + bookId + " is not in the cart");
        }
        
        // update quantity
        if(quantity <= 0){
            // remove item if quantity is 0 or negative
            Cart updatedCart = database.removeCartItem(customerId, bookId);
            if(updatedCart == null){
                throw new OutOfStockException("Not enough stock available for book with ID " + bookId);
            }
            return updatedCart;
        } else{
            // update quantity
            Cart updatedCart = database.updateCartItem(customerId, bookId, quantity);
            if(updatedCart == null){
                throw new OutOfStockException("Not enough stock available for book with ID " + bookId);
            }
            return updatedCart;
        }
    }
        
        @DELETE
        @Path("/items/{bookId}")
        public Response removeCartItem(
                                @PathParam("customerId") Long customerId,
                                @PathParam("bookId") Long bookId){
            
            // check if cart exists
            if(!database.customerExists(customerId)){
                throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist");
            }
            
            // remove item
            database.removeCartItem(customerId,bookId);
            return Response.status(Response.Status.NO_CONTENT).build();
                        
        }
}
