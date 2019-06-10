package sample;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SignIn signIn = new SignIn();
        //SupervisorGUI supervisorGUI = new SupervisorGUI(1,"Durres", 1);
    }
    public static void main(String[] args) {
        launch(args);
    }

}