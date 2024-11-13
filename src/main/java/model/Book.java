package model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate;
    private Integer stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock){
        if (stock != null) {
            this.stock = stock;
        } else {
            this.stock = 0; // Valoare implicită dacă stock este null
        }
    }


    @Override
    public String toString(){
        return"Book Id: "+ id + " Title: " + title + " Author: " + author + " Published date: " + publishedDate + " Stock: " + stock;
    }
}
