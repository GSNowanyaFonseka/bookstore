/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstore.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hp
 */
public class Author {
    
    private Long id;
    private String name;
    private String biography;
    private List<Long> bookIds;
    
    // defalut constructor for deserialization
    public Author(){
        this.bookIds = new ArrayList<>();
    }
    
    // constructor with parameters
    public Author(Long id, String name, String biography){
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.bookIds = new ArrayList<>();
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
    
    // Helper methods
    public void addBookId(Long bookId) {
        if (!this.bookIds.contains(bookId)) {
            this.bookIds.add(bookId);
        }
    }
    
    public void removeBookId(Long bookId) {
        this.bookIds.remove(bookId);
    }
}
