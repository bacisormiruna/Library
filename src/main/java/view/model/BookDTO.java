package view.model;


import javafx.beans.property.*;

import java.math.BigDecimal;

//pentru partea de Binding Data -> preluarea datelor din baza de date pentru maparea in interfata ca sa se creeze dinamic, in mod automat
public class BookDTO {
    //normal nu setez un id pentru ca se genereaza automat, dar am nevoie pentru salvarea in baza de date ca mai apoi sa gasesc cartea pe care vreau sa o vand
    private LongProperty idProperty;
    public void setId(Long id){
        idProperty().set(id);
    }

    public Long getId(){
        return idProperty().get();
    }

    public LongProperty idProperty(){
        if (idProperty == null){
            idProperty = new SimpleLongProperty(this,"id");
        }
        return idProperty;
    }
    private StringProperty author;

    public void setAuthor(String author){
        authorProperty().set(author);
    }

    public String getAuthor(){
        return authorProperty().get();
    }

    public StringProperty authorProperty(){
        if (author == null){
            author = new SimpleStringProperty(this,"author");
        }
        return author;
    }

    private StringProperty title;

    public void setTitle(String title){
        titleProperty().set(title);
    }
    public String getTitle(){
        return titleProperty().get();
    }

    public StringProperty titleProperty(){
        if (title == null){
            title = new SimpleStringProperty(this,"title");
        }
        return title;
    }

    private IntegerProperty stock;

    public void setStock(Integer stock){
        if (stock != null) {
            stockProperty().set(stock);
        } else {
            stockProperty().set(0);
        }
    }
    public Integer getStock(){
        return stockProperty().get();
    }

    public IntegerProperty stockProperty(){
        if (stock==null){
            stock = new SimpleIntegerProperty(this,"stock");
        }
        return stock;
    }

    private DoubleProperty price;

    public DoubleProperty priceProperty() {
        if (price == null) {
            price = new SimpleDoubleProperty(this, "price");
        }
        return price;
    }

    public void setPrice(Double price) {
        priceProperty().set(price);
    }

    public Double getPrice() {
        return priceProperty().get();

    }
}
