package com.example.bookapp.data;

public class Book {
    private int id;
    private String bookTitle;
    private String authorName;
    private int totalPages;

    public Book() {
    }

    public Book(int id, String bookTitle, String authorName, int totalPages) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.totalPages = totalPages;
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
}