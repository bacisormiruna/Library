package launcher;

import controller.BookController;
import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage; //componenta de baza pentru JavaFx
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthentificationService;
import service.user.AuthentificationServiceMySQL;
import view.BookView;
import view.LoginView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class EmployeeComponentFactory { //clasa Singleton
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static EmployeeComponentFactory instance;


    public static EmployeeComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage){
        if (instance==null){
            synchronized (EmployeeComponentFactory.class){
                if (instance == null) {
                    instance = new EmployeeComponentFactory(componentsForTest, primaryStage);
                }
            }
        }
        return instance;
    }

    //modific din public in private constructorul
    private EmployeeComponentFactory(Boolean componentsForTest, Stage primaryStage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection());
        UserRepository userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        AuthentificationService authentificationService = new AuthentificationServiceMySQL(userRepository, rightsRolesRepository);
        Long userId = authentificationService.getCurrentUserId();
        this.bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection), new Cache<>());
        this.bookService = new BookServiceImpl(bookRepository, authentificationService);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(this.bookService.findAll());
        this.bookView = new BookView(primaryStage, bookDTOs);
        this.bookController = new BookController(bookView, bookService);

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
