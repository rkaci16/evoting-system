package sample;

import db.UserDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AlreadyVoted;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class VoterGui {
    public VoterGui(){
        start();
    }
    private void start(){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("E-VOTING VOTER");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1500, 800);
        grid.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
        Map<String,Integer> map = new HashMap <String, Integer>();
        ToggleGroup group = new ToggleGroup();
        RadioButton[] radioButtons = new RadioButton[50];
        int rbIndex = 0;
        int i = 0;
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
        try {
            String sql = "Select name, leader from Candidate";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                RadioButton radioButton = new RadioButton(rs.getString(1));
                radioButton.setToggleGroup(group);
                radioButtons[rbIndex] = radioButton;
                String candidate = rs.getString(1);
                map.put(candidate,0);
                rbIndex++;
                grid.add(radioButton,0, i);
                i+=5;
            }
            // You can also validate user by result size if its comes zero user is invalid else user is valid

        } catch (SQLException err) {
        }
        if(conn !=null) {
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }


        Button vote = new Button("Vote");

        int finalRbIndex = rbIndex;
        vote.setOnAction(new EventHandler <ActionEvent>() {
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
                    conn = DriverManager.getConnection(url, "geiWbVZPjZ", "uV0o55bynF");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                PreparedStatement stmt = null;
                ResultSet rs;
                try {
                    String sql = "Select * from already_voted";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        if(rs.getInt("hasVoted") == 1){
                            sql = "Select * from votingStatus";
                            stmt = conn.prepareStatement(sql);
                            rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                if(rs.getInt(1) == 1) {

                                    int flag = 0;
                                    for (int i = 0; i < finalRbIndex; i++) {
                                        if (radioButtons[i].isSelected()) {
                                            flag = 1;
                                            String key = radioButtons[i].getText();
                                            map.put(key, map.get(key) + 1);
                                            AlertGUI alertGUI = new AlertGUI();
                                            alertGUI.showAlertMessage("Submission", "Vote submitted");
                                            UserDB userDB = new UserDB();
                                            userDB.updateAlreadyVoted(new AlreadyVoted(0));
                                            break;
                                        } else continue;
                                    }
                                    if (flag == 0) {
                                        AlertGUI alertGUI = new AlertGUI();
                                        alertGUI.showAlertMessage("Alert", "Please select a candidate");
                                    }
                                }
                                else{
                                    AlertGUI alertGUI = new AlertGUI();
                                    alertGUI.showAlertMessage("Alert", "Voting has not started yet or has finished!");
                                }

                            }

                        }
                        else {
                            AlertGUI alertGUI = new AlertGUI();
                            alertGUI.showAlertMessage("Alert", "Ask permission!");
                        }

                    }

                } catch (Exception err) {
            }
                if(conn !=null) {
                try {
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            }
        });

        Button bShowResults = new Button("Show Results");

        grid.add(vote,0,i);
        grid.add(bShowResults, 0,i+5);



        bShowResults.setOnAction(new EventHandler <ActionEvent>() {
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
                try {
                    String sql = "Select * from votingStatus";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery(sql);
                    if(rs.next()) {
                        if(rs.getInt(1) == 0){
                            final int[] j = {0};
                            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                                Label lCandidate = new Label(entry.getKey() + " : " + entry.getValue());
                                grid.add(lCandidate,20, j[0]);
                                j[0] += 5;
                            }

                        }
                        else {
                            AlertGUI alertGUI = new AlertGUI();
                            alertGUI.showAlertMessage("Alert", "Voting has not ended yet!");
                        }
                    }
                    // You can also validate user by result size if its comes zero user is invalid else user is valid

                } catch (SQLException err) {
                }
                if(conn !=null) {
                    try {
                        conn.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });

        primaryStage.setTitle("Home Page");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
