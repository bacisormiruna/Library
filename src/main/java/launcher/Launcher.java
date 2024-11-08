package launcher;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args){ //asa functioneaza JavaFx da launch aplicatiei
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentFactory.getInstance(false, primaryStage);
    }
}
