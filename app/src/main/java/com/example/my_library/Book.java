// Book.java
package com.example.my_library;

public class Book {
    private int id;
    private String name;
    private String author;
    private String publication;
    private int quantity;

    // Constructor with all fields
    public Book(int id, String name, String author, String publication, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publication = publication;
        this.quantity = quantity;
    }

    // Constructor without ID (if needed)
    public Book(String name, String author, String publication, int quantity) {
        this.name = name;
        this.author = author;
        this.publication = publication;
        this.quantity = quantity;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Setter for ID
    public void setId(int id) {
        this.id = id;
    }

    // Other getters and setters
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublication() {
        return publication;
    }

    public int getQuantity() {
        return quantity;
    }
}
