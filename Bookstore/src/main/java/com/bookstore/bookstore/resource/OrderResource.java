/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.db.InMemoryDatabase;
import com.bookstore.bookstore.exception.CartNotFoundException;
import com.bookstore.bookstore.exception.CustomerNotFoundException;
import com.bookstore.bookstore.exception.OrderNotFoundException;
import com.bookstore.bookstore.exception.OutOfStockException;
import com.bookstore.bookstore.model.Cart;
import com.bookstore.bookstore.model.Order;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * RESTful APIs for managing customer orders
 * provides endpoints for retrieve customer orders, retrieve a specific order and create an order from customer's cart
 * @author Hp
 */
@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    
    private static final Logger LOGGER = Logger.getLogger(OrderResource.class.getName());
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    
    /**
     * Retrieves all orders for a given customer
     * @param customerId the ID of the customer
     * @return List of orders for the customer
     */
    @GET
    public List<Order> getCustomerOrders(@PathParam("customerId")Long customerId){
        LOGGER.log(Level.INFO, "Fetching all orders for customer with ID: {0}", customerId);
        // check if customer exists
        if(!database.customerExists(customerId)){
            LOGGER.log(Level.WARNING, "Customer with ID {0} not found", customerId);
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist");
        }
        
        return database.getOrderByCustomerId(customerId);
    }
    
    /**
     * retrieves a specific order for a given customer
     * @param customerId the ID of the customer
     * @param orderId ID of the order
     * @return requested Order
     */
    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("customerId") Long customerId, @PathParam("orderId") Long orderId){
        LOGGER.log(Level.INFO,"Fetching order with ID: {0} for customer ID: {1}", new Object[]{orderId,customerId});
        // check if customer exists
        if(!database.customerExists(customerId)){
            LOGGER.log(Level.WARNING,"Customer with ID {0} not found", customerId);
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist");
        }
        
        Order order = database.getOrderById(customerId, orderId);
        if(order == null){
            LOGGER.log(Level.WARNING," Order with ID {0} not found for customer with ID {1} ", new Object[]{orderId,customerId});
            throw new OrderNotFoundException("Order with ID " + orderId + " not found for customer with ID " + customerId);
        }
        
        return order;
    }
    
    /**
     * creates a new order for a given customer from their cart
     * @param customerId Id of the customer
     * @return Response containing the created order
     */
    @POST
    public Response createOrder(@PathParam("customerId") Long customerId){
        
        LOGGER.log(Level.INFO,"Creating order for customer ID: {0}", customerId);
        // check if customer exists
        if(!database.customerExists(customerId)){
            LOGGER.log(Level.WARNING,"Customer with ID {0} not found", customerId);
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist");
        }
        
        // check if cart exist
        Cart cart = database.getCartByCustomerId(customerId);
        if(cart == null || cart.getItems().isEmpty()){
            LOGGER.log(Level.WARNING,"Cart is empty or not found for customer ID: {0}", customerId);
            throw new CartNotFoundException("Cart is empty or does not exist for customer with ID " + customerId);
        }
        
        // create order from cart
        Order order = database.createOrderFromCart(customerId);
        if(order == null){
            LOGGER.log(Level.WARNING,"Failed to create order due to out of stock items for customer ID: {0}", customerId);
            throw new OutOfStockException("Some items in the cart are out of stock");
        }
        
        LOGGER.log(Level.INFO,"Order created successfully for customer ID: {0}", customerId);
        return Response.status(Response.Status.CREATED)
                                .entity(order)
                                .build();
    }
}

