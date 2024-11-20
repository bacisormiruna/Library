import controller.LoginController;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthentificationService;
import service.user.AuthentificationServiceMySQL;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    public static void main(String[] args){ launch(args);}

    //    System.out.println("Hello world!");

    /*    Book book=new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1920,10,20))
                .build();
        System.out.println(book);
*/
      /*  BookRepository bookRepository=new BookRepositoryMock();

        bookRepository.save(book);
        bookRepository.save(new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1930,12,4)).build());
        System.out.println(bookRepository.findAll());
        bookRepository.removeAll();
        System.out.println(bookRepository.findAll());*/
        //Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        //BookRepository bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection),new Cache<>());
        //BookService bookService=new BookServiceImpl(bookRepository);


        //bookRepository.save(book);
        //System.out.println(bookRepository.findAll());
        //System.out.println(bookRepository.findAll());
        //Book bookMoaraCuNoroc=new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1930,12,4)).build();
        //bookService.save(bookMoaraCuNoroc);
        //System.out.println(bookService.findAll());
        //bookService.delete(bookMoaraCuNoroc);
        //bookService.delete(book);
       // bookService.save(book);
       // System.out.println(bookService.findAll());
     /*   Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true ).getConnection();
        RightsRolesRepository rightsRolesRepository= new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        AuthentificationService authentificationService = new AuthentificationServiceMySQL(userRepository, rightsRolesRepository);

        if (userRepository.existsByUsername("Miruna")){
            System.out.println("Username already exists!");
        }else {
            authentificationService.register("Miruna", "parola123");
        }
        System.out.println(authentificationService.login("Miruna", "parola123"));
    }*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);

        final AuthentificationService authentificationService = new AuthentificationServiceMySQL(userRepository, rightsRolesRepository);

        final LoginView loginView = new LoginView(primaryStage);
        final UserValidator userValidator= new UserValidator(userRepository);
        new LoginController(loginView, authentificationService, userValidator);
        }
}
