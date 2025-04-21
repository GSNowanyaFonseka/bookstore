/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.db.InMemoryDatabase;
import com.bookstore.bookstore.exception.CartNotFoundException;
import com.bookstore.bookstore.exception.CustomerNotFoundException;
import com.bookstore.bookstore.exception.OutOfStockException;
import com.bookstore.bookstore.model.Cart;
import com.bookstore.bookstore.model.Order;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Hp
 */
@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    
    @GET
    public List<Order> getCustomerOrders(@PathParam("customerId")Long customerId){
        // check if customer exists
        if(!database.customerExists(customerId)){
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist");
        }
        
        return database.getOrderByCustomerId(customerId);
    }
    
    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("customerId") Long customerId, @PathParam("orderId") Long orderId){
        // check if customer exists
        if(!database.customerExists(customerId)){
            throw new WebApplicationException("Customer with ID " + customerId + " does not exist");
        }
        
        Order order = database.getOrderById(customerId, orderId);
        if(order == null){
            throw new WebApplicationException("Order with ID " + orderId + " not found for customer with ID " + customerId, Response.Status.NOT_FOUND);
        }
        
        return order;
    }
    
    @POST
    public Response createOrder(@PathParam("customerId") Long customerId){
        // check if customer exists
        if(!database.customerExists(customerId)){
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist");
        }
        
        // check if cart exist
        Cart cart = database.getCartByCustomerId(customerId);
        if(cart == null || cart.getItems().isEmpty()){
            throw new CartNotFoundException("Cart is empty or does not exist for customer with ID " + customerId);
        }
        
        // create order from cart
        Order order = database.createOrderFromCart(customerId);
        if(order == null){
            throw new OutOfStockException("Some items in the cart are out of stock");
        }
        
        return Response.status(Response.Status.CREATED)
                                .entity(order)
                                .build();
    }
}

