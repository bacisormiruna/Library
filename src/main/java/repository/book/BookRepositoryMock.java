package repository.book;

import model.Book;
import model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Optional.empty();//inlocuitor pentru null
public class BookRepositoryMock implements BookRepository{
    //emulam o baza de date folosind un ArrayList
    private final List<Book> books;//List este o interfata, iar ArrayList este o clasa/ o implementare
    //am folosit final pentru a seta imutabilitatea
    private final List<Order> orders;
    public BookRepositoryMock(){
        books=new ArrayList<>();
        orders =new ArrayList<>();
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
        //return books.add(book);
        boolean result = books.add(book);
        if (result) {//pentru sale si update din aceeasi rulare
            refresh(findAll()); // Metodă care încarcă datele din baza de date
        }
        return result;
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
                    it.setPrice((bookUpdated.getPrice()));
                    it.setStock(bookUpdated.getStock());
                    return true;
                })
                .orElse(false);
    }



    public void refresh(List<Book> booksFromDatabase) {
        books.clear();
        books.addAll(booksFromDatabase);
    }

    @Override
    public boolean saveOrder(Long userId, String title, String author, double totalPrice, int numberOfExemplars) {
        // Căutăm cartea pe baza titlului și autorului
        Optional<Book> bookOptional = books.parallelStream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author))
                .findFirst();

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            // Verificăm dacă stocul este suficient
            if (book.getStock() >= numberOfExemplars) {
                // Actualizăm stocul cărții
                book.setStock(book.getStock() - numberOfExemplars);

                // Creăm și salvăm comanda
                Order order = new Order(userId, book.getId(), title, author, totalPrice, numberOfExemplars);
                orders.add(order);

                // Reîmprospătăm lista de cărți
                refresh(findAll());
                return true;
            } else {
                System.out.println("Stoc insuficient pentru cartea: " + title + " de " + author);
            }
        } else {
            System.out.println("Cartea " + title + " de " + author + " nu există.");
        }
        return false;
    }


}
