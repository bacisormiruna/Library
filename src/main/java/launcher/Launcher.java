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
            ComponentFactory componentFactory = ComponentFactory.getInstance(false, primaryStage);
    }
}
