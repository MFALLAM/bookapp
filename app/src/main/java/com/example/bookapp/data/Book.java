package com.example.bookapp.data;

public class Book {
    private int id;
    private String bookTitle;
    private String authorName;
    private int totalPages;
    private String timestamp;


    public Book(int id, String bookTitle, String authorName, int totalPages, String timestamp) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.totalPages = totalPages;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}