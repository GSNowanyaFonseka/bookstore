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
    private String firstname;
    private String lastname;
    private String biography;
    private List<Long> bookIds;
    
    // defalut constructor for deserialization
    public Author(){
        this.bookIds = new ArrayList<>();
    }
    
    // constructor with parameters
    public Author(Long id, String firstname, String lastname, String biography){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
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
