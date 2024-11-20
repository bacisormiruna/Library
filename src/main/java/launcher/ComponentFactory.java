package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage; //componenta de baza pentru JavaFx
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.BookView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class ComponentFactory { //clasa Singleton
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static ComponentFactory instance;

    //!! de modificat ca in laborator
    public static ComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage){//lazy load
        if (instance==null){//multithreading safe
            synchronized (ComponentFactory.class){
                if (instance == null) {
                    instance = new ComponentFactory(componentsForTest, primaryStage);
                }
            }
        }
        return instance;
    }

    //modific din public in private constructorul
    private ComponentFactory(Boolean componentsForTest, Stage primaryStage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView = new BookView(primaryStage,bookDTOs);
        this.bookController = new BookController(bookView, bookService); //interactionam doar cu Service, niciodata cu Repository
    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }
}
