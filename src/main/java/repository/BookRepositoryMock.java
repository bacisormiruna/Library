package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Optional.empty();//inlocuitor pentru null
public class BookRepositoryMock implements BookRepository{
    private final List<Book> books;//List este o interfata, iar ArrayList este o clasa/ o implementare
    //am folosit final pentru a seta imutabilitatea
    public BookRepositoryMock(){
        books=new ArrayList<>();
    }
    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {//Long este un obiect
        return books.parallelStream()//mai rapid cand avem o lista cu foarte multe elemente ->Multithreading
                .filter(it->it.getId().equals(id))
                .findFirst(); //returneaza direct un Optional
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }
}
