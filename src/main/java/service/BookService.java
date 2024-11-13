package service;

import model.Book;

import java.util.List;

//defineste ce poate folosi presentation din service
//ce poate folosi presentation din service
public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    boolean save(Book book);
    boolean delete(Book book);
    int getAgeOfBook(Long id);//o parte de algoritm/logica
    boolean sale(Long bookId , int quantity);
}
