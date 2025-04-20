/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.db.InMemoryDatabase;
import com.bookstore.bookstore.exception.CustomerNotFoundException;
import com.bookstore.bookstore.exception.InvalidInputException;
import com.bookstore.bookstore.model.Customer;
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
import java.util.regex.Pattern;

/**
 *
 * @author Hp
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private InMemoryDatabase database = InMemoryDatabase.getInstance();
    
    //Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    @GET
    public List<Customer> getAllCustomers(){
        return database.getAllCustomers();
    }
    
    @GET
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") Long id){
        Customer customer = database.getCustomerById(id);
        if(customer == null){
            throw new CustomerNotFoundException("Customer with ID " + id + " does not exist");
        }
        return customer;
    }
    
    @POST
    public Response createCustomer(Customer customer){
        // validate input
        validateCustomer(customer);
        
        Customer createdCustomer = database.addCustomer(customer);
        if(createdCustomer == null){
            throw new InvalidInputException("Email already in use");
        }
        
        return Response.status(Response.Status.CREATED)
                .entity(createdCustomer)
                .build();
    }
    
    @PUT
    @Path("/{id}")
    public Customer updateCustomer(@PathParam("id")Long id, Customer customer){
        // check if customer exists
        if(!database.customerExists(id)){
            throw new CustomerNotFoundException("Customer with ID " + id + " does not exist.");
        }
        
        // validate input
        validateCustomer(customer);
        
        // set ID from path
        customer.setId(id);
        
        Customer updatedCustomer = database.updateCustomer(customer);
        if(updatedCustomer == null){
            throw new InvalidInputException("Email already in use by another customer");
        }
        
        return updatedCustomer;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id")Long id){
        boolean deleted = database.deleteCustomer(id);
        if(!deleted){
            throw new CustomerNotFoundException("Customer with ID " + id + " does not exist");
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    // Helper method to validate customer information
    private void validateCustomer(Customer customer){
        if(customer.getName() == null || customer.getName().trim().isEmpty()){
            throw new InvalidInputException("Customer name is required");
        }
        
        if(customer.getEmail() == null || customer.getEmail().trim().isEmpty()){
            throw new InvalidInputException("Customer email is required");
        }
        
        if(!EMAIL_PATTERN.matcher(customer.getEmail()).matches()){
            throw new InvalidInputException("Invalid email format");
        }
        
        if(customer.getPassword() == null || customer.getPassword().length()<6){
            throw new InvalidInputException("Password must be at least 6 characters long");
        }
    }
}


