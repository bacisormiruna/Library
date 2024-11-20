package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.User;
import model.validation.Notification;
import model.validation.UserValidator;
import service.user.AuthentificationService;
import view.BookView;
import view.LoginView;

import java.util.List;

public class LoginController {
    private final LoginView loginView;
    private final AuthentificationService authenticationService;
    private Scene bookScene;
    private Stage window;
    private final BookView bookView;

    public LoginController(LoginView loginView, AuthentificationService authenticationService, Stage window, BookView bookView) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.bookView=bookView;
        this.bookScene = new Scene(bookView, 600, 600);

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
                Scene bookScene = new Scene(bookView, 600, 600);

                // Schimbă scena
                window.setScene(bookScene);
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

