package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.Book;
import model.User;
import model.validation.Notification;
import model.validation.UserValidator;
import service.book.BookService;
import service.user.AuthentificationService;
import view.BookView;
import view.LoginView;
import view.model.BookDTO;

import java.util.List;

import static mapper.BookMapper.convertBookListToBookDTOList;

public class LoginController {
    private final LoginView loginView;
    private final AuthentificationService authenticationService;
   // private final Scene bookScene;
    private final Stage stage;
    private final BookView bookView;
    private final BookService bookService;

    public LoginController(LoginView loginView, AuthentificationService authenticationService, Stage stage, BookView bookView, BookService bookService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.stage=stage;
        this.bookView=bookView;
       // this.bookScene = new Scene(bookView.getBookTableView(), 600, 600);
        this.bookService=bookService;
        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Login Successfull!");
                //Scene bookScene = new Scene(bookView, 600, 600);
                Stage stage1 = stage;  // Stage-ul existent transmis prin constructor
                List<Book> updatedBooks = bookService.findAll();
                BookView newBookView = new BookView(stage1, convertBookListToBookDTOList(updatedBooks), new GridPane());  // Creăm o nouă instanță de BookView cu lista de cărți
                Scene newScene = stage.getScene();//new Scene(newBookView.getBookTableView(), 600, 600);  // Creăm o scenă cu BookView
                stage.setScene(newScene);

            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);
            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successfull! ");
            }
        }
    }
}