/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.db;

import com.bookstore.bookstore.model.Author;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Hp
 */
public class InMemoryDatabase {
    // singleton instance
    private static InMemoryDatabase instance;
    
    // storage collections
    // book storage
    private Map<Long, Book> books = new HashMap<>();
    // Author storage
    private Map<Long, Author> authors = new HashMap<>();
    // customer storage
    private Map<Long, Customer> customers = new HashMap<>();
    
    
    // ID generators
    // book Id generator
    private AtomicLong bookIdGenerator = new AtomicLong(1);
    // author Id generator
    private AtomicLong authorIdGenerator = new AtomicLong(1);
    // customer Id generator
    private AtomicLong customerIdGenerator = new AtomicLong(1);
    
    // private constructor for singleton
    private InMemoryDatabase(){
      
    }
    
    // get singleton instance
    public static synchronized InMemoryDatabase getInstance(){
        if(instance == null){
            instance = new InMemoryDatabase();
        }
        return instance;
    }
    
    // book operations
    public List<Book> getAllBooks(){
        return new ArrayList<>(books.values());
    }
    
    public Book getBookId(Long id){
        return books.get(id);
    }
    
    public Book addBook(Book book){
        Long id = bookIdGenerator.getAndIncrement();
        book.setId(id);
        books.put(id,book);
        return book;
    }
    
    public Book updateBook(Book book){
        if(books.containsKey(book.getId())){
            books.put(book.getId(),book);
            return book;
        }
        return null;
    }
    
    public boolean deleteBook(Long id){
        if(books.containsKey(id)){
            books.remove(id);
            return true;
        }
        return false;
    }
    
    // Author operations
    public List<Author>getAllAuthors(){
        return new ArrayList<>(authors.values());
    }
    
    public Author getAuthorById(Long id){
        return authors.get(id);
    }
    
    public Author addAuthor (Author author){
        Long id = authorIdGenerator.getAndIncrement();
        author.setId(id);
        authors.put(id, author);
        return author;
    }
    
    public Author updateAuthor(Author author){
        if(authors.containsKey(author.getId())){
            authors.put(author.getId(),author);
            return author;
        }
        return null;
    }
    
    public boolean deleteAuthor(Long id){
        if(authors.containsKey(id)){
            authors.remove(id);
            return true;
        }
        return false;
    }
    
    // get books by author ID
    public List<Book>getBooksByAuthorId(Long authorId){
        List<Book> authorBooks = new ArrayList<>();
        for(Book book : books.values()){
            if(book.getAuthorId() != null && book.getAuthorId().equals(authorId)){
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }
    
    // check is author exists
    public boolean authorExists(Long id){
        return authors.containsKey(id);
    }
    
    // Customer operations
    public List<Customer> getAllCustomers(){
        return new ArrayList<>(customers.values());
    }
    
    public Customer getCustomerById(Long id){
        return customers.get(id);
    }
    
    public Customer addCustomer(Customer customer){
        // check if email already exists
        for(Customer existingCustomer : customers.values()){
            if(existingCustomer.getEmail().equals(customer.getEmail())){
                return null;  // email already exists
            }
        }
        
        Long id = customerIdGenerator.getAndIncrement();
        customer.setId(id);
        customers.put(id,customer);
        return customer;
    }
    
    public Customer updateCustomer(Customer customer){
        // check if email is already used by another customer
        for(Customer existingCustomer : customers.values()){
            if(existingCustomer.getEmail().equals(customer.getEmail()) && 
                    !existingCustomer.getId().equals(customer.getId())){
                return null; // email already used by another customer
            }
        }
        
        if(customers.containsKey(customer.getId())){
            customers.put(customer.getId(), customer);
            return customer;
        }
        return null;
    }
    
    public boolean deleteCustomer(Long id){
        if(customers.containsKey(id)){
            customers.remove(id);
            return true;
        }
        return false;
    }
    
    // check if customer exists
    public boolean customerExists(Long id){
        return customers.containsKey(id);
    }
    
}
