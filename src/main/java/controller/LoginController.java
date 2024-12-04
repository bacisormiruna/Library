package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import launcher.AdminComponentFactory;
import launcher.EmployeeComponentFactory;
import launcher.LoginComponentFactory;
import model.Book;
import model.Role;
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
    private User curentUser;

    public LoginController(LoginView loginView, AuthentificationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.curentUser=new User();
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
                loginView.setActionTargetText("Login Successful!");
                curentUser = loginNotification.getResult();
                
                List<Role> roles = curentUser.getRoles();
                if (roles != null && !roles.isEmpty()) {

                    String role = roles.get(0).getRole();
                    switch (role) {
                        case "employee":
                            EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                            break;
                        case "administrator":
                            AdminComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                            break;
                        default:
                            LoginComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
                            break;
                    }
                } else {
                    loginView.setActionTargetText("User has no assigned role.");
                    System.out.println("No roles assigned to user.");
                }
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