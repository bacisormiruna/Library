package service.book;

import model.Book;
import repository.book.BookRepository;
import service.book.BookService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
//Service poate vedea si folosi repository
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Book with id: %d was not found.".formatted(id)));
    }

    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        return bookRepository.delete(book);
    }

    @Override
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id);
        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
    @Override
    public boolean sale(Long bookId, int quantity) {
        if (quantity>0){
        Book book = this.findById(bookId);//se fac verificarile o singura data, se cauta dupa id cartea de vandut
        if (book.getStock() >= quantity) {
            book.setStock(book.getStock() - quantity);
            return bookRepository.update(book);
            }
        }
        return false;
    }
}