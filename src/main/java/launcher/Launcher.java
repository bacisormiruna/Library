package launcher;

import controller.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;
import model.validation.Notification;
import model.validation.UserValidator;
import service.user.AuthentificationService;
import service.user.AuthentificationServiceMySQL;
import view.BookView;
import view.LoginView;
import view.model.BookDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Launcher extends Application {

    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            ComponentFactory componentFactory = ComponentFactory.getInstance(false, primaryStage);
            // Creează instanțele necesare
           /* AuthentificationService authenticationService = new AuthentificationServiceMySQL(componentFactory.getUserRepository(), componentFactory.getRightsRolesRepository());
            LoginView loginView = new LoginView(primaryStage);

            // Creează scena pentru LoginView
            Scene loginScene = new Scene(loginView.getLoginPane(), 600, 400);

            // Creează BookView, dar nu-l adăuga la scenă încă
            List<BookDTO> books = new ArrayList<>();
            BookView bookView = new BookView(primaryStage, books);

            // Adaugă un handler pentru butonul de login din LoginView
            loginView.setLoginAction(event -> {
                String username = loginView.getUsername();
                String password = loginView.getPassword();

                // Folosește AuthenticationService pentru a verifica utilizatorul
                Notification<User> loginNotification = authenticationService.login(username, password);

                if (loginNotification.hasErrors()) {
                    // Dacă autentificarea nu este validă, afișează mesajul de eroare
                    loginView.setActionTargetText("Username sau password incorect!");
                } else {
                    // Dacă autentificarea este validă, schimbă scena la BookView
                    loginView.setActionTargetText("Login successful!");
                    Scene bookScene = new Scene(bookView, 600, 600);
                    primaryStage.setScene(bookScene);
                }
            });

            // Setează scena inițială pe LoginView
            primaryStage.setTitle("Login App");
            primaryStage.setScene(loginScene);
            primaryStage.show();*/
        } catch (Exception e) {
            // Capturarea erorilor și imprimarea mesajelor
            System.err.println("A apărut o eroare: " + e.getMessage());
            e.printStackTrace(); // Afișează detalii complete despre eroare
        }
    }
}
