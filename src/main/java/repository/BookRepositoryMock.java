package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Optional.empty();//inlocuitor pentru null
public class BookRepositoryMock implements BookRepository{
    //emulam o baza de date folosind un ArrayList
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

    @Override //trebuia sa am o metoda implementata de update si in Mock
    public boolean update(Book bookUpdated) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(bookUpdated.getId()))
                .findFirst()
                .map(it -> {//daca findFirst gaseste o carte atunci se executa cu map actualizarea campurilor din tabela corespunzatoare acelei carti cu valorile corespunzatoare din obiectul Book transmis ca si parametru metodei
                    it.setTitle(bookUpdated.getTitle());
                    it.setAuthor(bookUpdated.getAuthor());
                    it.setPublishedDate(bookUpdated.getPublishedDate());
                    it.setStock(bookUpdated.getStock());
                    return true;
                })
                .orElse(false);
    }
}
