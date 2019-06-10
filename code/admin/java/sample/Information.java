package sample;

import db.UserDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Citizen;
import model.VotingStatus;

import java.sql.*;

public class Information {
    public Information(Stage primaryStage, Scene oldScene) { GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);

        Button bBack = new Button("Back");


        bBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                {
                    primaryStage.setScene(oldScene);
                    primaryStage.setTitle("Home Page");
                }
            }
        });
        Button bVotingStatus = new Button("Start / End Voting");
        bVotingStatus.setOnAction(new EventHandler <ActionEvent>() {
            UserDB userDB = new UserDB();
            @Override
            public void handle(ActionEvent event) {
                Connection conn = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                String url = "jdbc:mysql://remotemysql.com/geiWbVZPjZ";
                try {
                    conn = DriverManager.getConnection(url,"geiWbVZPjZ", "uV0o55bynF");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                PreparedStatement stmt = null;
                ResultSet rs;
                String sql = "Select * from votingStatus";
                try {
                    stmt = conn.prepareStatement(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    rs = stmt.executeQuery(sql);
                    if(rs.next()){
                        if(rs.getInt(1  ) == 0){
                            userDB.updateVotingStatus(new VotingStatus(1));
                            sql = "SELECT * FROM citizien";
                            stmt = conn.prepareStatement(sql);
                            rs = stmt.executeQuery(sql);
                            while(rs.next()){
                                UserDB userDB = new UserDB();
                                userDB.updateCitizen(new Citizen(rs.getInt("id"), 0));
                            }



                            AlertGUI alertGUI = new AlertGUI();
                            alertGUI.showAlertMessage("Voting Status changed", "Voting Process has STARTED!");

                        }
                        else{
                            userDB.updateVotingStatus(new VotingStatus(0));
                            AlertGUI alertGUI = new AlertGUI();
                            alertGUI.showAlertMessage("Voting Status changed", "Voting Process has ENDED!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        Scene scene = new Scene(gridPane, 1500, 800);

        gridPane.add(bBack,0,0);
        gridPane.add(bVotingStatus,0,1);

        gridPane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));

        primaryStage.setTitle("Manage Candidates");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
