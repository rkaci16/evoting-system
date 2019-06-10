package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainAdmin {
    public MainAdmin(){start();}

    private void start(){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("E-VOTING ADMINISTRATOR");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button bVotingCenters = new Button("Voting Centers");
        bVotingCenters.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bVotingCenters.setMinSize(0,0);
        bVotingCenters.setPrefSize(250,250);
        bVotingCenters.setAlignment(Pos.CENTER);

        Button bCandidates = new Button("Candidates");
        bCandidates.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bCandidates.setMinSize(0,0);
        bCandidates.setPrefSize(250,250);
        bCandidates.setAlignment(Pos.CENTER);

        HBox hbVcCand = new HBox(15);
        hbVcCand.getChildren().addAll(bVotingCenters,bCandidates);
        bCandidates.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bCandidates.setMinSize(0,0);
        bCandidates.setAlignment(Pos.CENTER);

        Button bSupervisors = new Button("Supervisors");
        bSupervisors.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bSupervisors.setMinSize(0,0);
        bSupervisors.setPrefSize(250,250);
        bSupervisors.setAlignment(Pos.CENTER);

        Button bInformation = new Button("Information");
        bInformation.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bInformation.setMinSize(0,0);
        bInformation.setPrefSize(250,250);
        bInformation.setAlignment(Pos.CENTER);

        HBox hbSupInfo = new HBox(15);
        hbSupInfo.getChildren().addAll(bSupervisors,bInformation);
        bCandidates.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bCandidates.setMinSize(0,0);
        bCandidates.setAlignment(Pos.CENTER);

        //Handler

        Scene scene = new Scene(grid, 1500, 800);
        bVotingCenters.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ManageCenters manageCenters= new ManageCenters(primaryStage, scene);
            }
        });

        bCandidates.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ManageCandidates manageCandidates= new ManageCandidates(primaryStage, scene);
            }
        });

        bCandidates.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ManageCandidates manageCandidates= new ManageCandidates(primaryStage, scene);
            }
        });

        bSupervisors.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ManageSupervisors manageSupervisors = new ManageSupervisors(primaryStage, scene);
            }
        });

        bInformation.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    Information information = new Information(primaryStage, scene);

            }
        });
        grid.add(hbVcCand,0,1);
        grid.add(hbSupInfo,0,2);

        grid.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));

        primaryStage.setTitle("Home Page");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
