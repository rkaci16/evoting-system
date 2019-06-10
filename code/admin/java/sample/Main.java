package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException {
       LogInAdmin logInAdmin = new LogInAdmin();
       // MainAdmin mainAdmin = new MainAdmin();

    }
    public static void main(String[] args) {
        launch(args);
    }

}