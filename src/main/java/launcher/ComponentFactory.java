package launcher;

import controller.BookController;
import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage; //componenta de baza pentru JavaFx
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
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

public class ComponentFactory { //clasa Singleton
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static ComponentFactory instance;
    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthentificationService authentificationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;


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
        this.rightsRolesRepository=new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        this.authentificationService= new AuthentificationServiceMySQL(userRepository,rightsRolesRepository);
        this.loginView= new LoginView(primaryStage);
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView = new BookView(primaryStage,bookDTOs,new GridPane());
        this.bookController = new BookController(bookView, bookService); //interactionam doar cu Service, niciodata cu Repository
        this.loginController= new LoginController(loginView,authentificationService,primaryStage, bookView, bookService);
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

    public LoginView getLoginView() {
        return loginView;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public AuthentificationService getAuthentificationService() {
        return authentificationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }
}
