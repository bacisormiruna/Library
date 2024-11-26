package repository.book;

import model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(Long id);//optional pentru cazul in care cartea cautata nu exista in baza de date
    boolean save(Book book);
    boolean delete(Book book);
    void removeAll();
    boolean update(Book book);
    boolean saveOrder(Long userId, String title, String author, double totalPrice, int numberOfExemplars);
}
