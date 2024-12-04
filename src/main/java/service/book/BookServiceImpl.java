package service.book;

import model.Book;
import repository.book.BookRepository;
import service.book.BookService;
import service.user.AuthentificationService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

//Service poate vedea si folosi repository
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthentificationService authentificationService;
    public BookServiceImpl(BookRepository bookRepository, AuthentificationService authentificationService){
        this.bookRepository = bookRepository;
        this.authentificationService = authentificationService;
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

    public boolean sale(Long bookId, int quantity) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            if (book.getStock() < quantity) {
                return false;
            }

            double price = book.getPrice();
            book.setStock(book.getStock() - quantity);
            boolean isUpdated = bookRepository.update(book);

            if (isUpdated) {
                Long userId = authentificationService.getCurrentUserId(); //sa imi ia userId-ul curent al userului care a vandut carti
                return bookRepository.saveOrder(userId, book.getTitle(), book.getAuthor(), price, quantity);
            }
        }
        return false;
    }

}
