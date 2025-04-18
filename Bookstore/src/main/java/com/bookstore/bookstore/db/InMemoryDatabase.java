/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.db;

import com.bookstore.bookstore.model.Author;
import com.bookstore.bookstore.model.Book;
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
    
    // ID generators
    // book Id generator
    private AtomicLong bookIdGenerator = new AtomicLong(1);
    // author Id generator
    private AtomicLong authorIdGenerator = new AtomicLong(1);
    
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
        if(authors.containsKey(author.getId())){
            authors.put(author.getId(), author);
            return author;
        }
        return null;
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
    
}
