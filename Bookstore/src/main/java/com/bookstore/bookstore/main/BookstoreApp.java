/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.main;

// import necessary classes for server setup
import org.glassfish.grizzly.http.server.HttpServer;   // creating http server
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;  // creating Grizzly server
import org.glassfish.jersey.jackson.JacksonFeature;   // enabling JSON support using Jackson
import org.glassfish.jersey.server.ResourceConfig;   // configuring REST resources

import java.io.IOException;   // handling IOExceptions
import java.net.URI;   // working with URIs
import java.util.logging.Logger;


// main class for starting the Bookstore application server
public class BookstoreApp {
    
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/api/";
    
    // logger instance for logging informations
    private static final Logger LOGGER = Logger.getLogger(BookstoreApp.class.getName());
    
    /**
     * starts the HTTP server
     * @return the started HttpServer
     */
    public static HttpServer startServer() {
        
        // Create a resource config that scans for JAX-RS resources and providers in com.bookstore package
        final ResourceConfig rc = new ResourceConfig().packages("com.bookstore");
        rc.register(JacksonFeature.class);
        
        // Create and start a new instance of grizzly http server instance to BASE_URI using resource config
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method to start the server and wait for user to stop
     * @throws IOException if error occurs
     */
    public static void main(String[] args) throws IOException {
        final HttpServer SERVER = startServer();
        System.out.println(String.format("Bookstore application started with endpoints available at %s\nHit enter to stop the server...", BASE_URI));
        LOGGER.info(String.format("Bookstore application started with endpoints available at %s\nHit enter to stop the server...", BASE_URI));
        System.in.read();
        SERVER.shutdownNow();
        LOGGER.info("Server stopped successfully");
    }
}