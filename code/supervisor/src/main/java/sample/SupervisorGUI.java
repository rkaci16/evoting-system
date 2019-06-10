package sample;

import db.UserDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AlreadyVoted;
import model.Citizen;

import java.sql.*;

public class SupervisorGUI {
    int id;
    String city;
    int centerNo;
    public SupervisorGUI(int id, String city, int centerNo) {
        this.id = id;
        this.city = city;
        this.centerNo = centerNo;
        start();
    }

    private void start() {

        Stage primaryStage = new Stage();
        primaryStage.setTitle("E-VOTING SUPERVISOR");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1500, 800);
        grid.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));


        final TextField tfName = new TextField();
        final TextField tfSurname = new TextField();
        final TextField tfPassportId = new TextField();
        final TextField tfId = new TextField();


        CheckBox checkBox = new CheckBox("Verify Infomaton");

        Button bSearch = new Button("Search");
        Button bSubmit = new Button("Submit");

        Label lCity = new Label("City: ");
        Label lCenterID = new Label("Center ID: ");
        Label lCenterNo = new Label("Center Number: ");
        Label lId = new Label("ID");
        Label lName = new Label("Name");
        Label lSurname = new Label("Surname");
        Label lPassportId = new Label("Passport ID");

        HBox hbFullName = new HBox();
        HBox hbPassportId = new HBox();
        HBox hbId = new HBox();

        hbFullName.getChildren().addAll(lName,tfName,lSurname,tfSurname,bSearch);
        hbPassportId.getChildren().addAll(lPassportId,tfPassportId);
        hbId.getChildren().addAll(lId,tfId);
        HBox hbInfo = new HBox();
        hbInfo.getChildren().addAll(lCenterID, new Label(String.valueOf(id)),lCity, new Label(city), lCenterNo, new Label(String.valueOf(centerNo)));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hbFullName,hbId,hbPassportId);

        tfId.setEditable(false);
        tfPassportId.setEditable(false);

        bSearch.setOnAction(new EventHandler <ActionEvent>() {
            public void handle(ActionEvent event) {
                if(tfName.getText() != null && tfSurname.getText() != null){
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
                    String name = tfName.getText();
                    String surname = tfSurname.getText();
                    try {
                            String sql = "Select * from citizien Where name='" + name + "' and surname='" + surname + "'";
                            stmt = conn.prepareStatement(sql);
                            rs = stmt.executeQuery(sql);
                            if (rs.next()) {
                                tfPassportId.setText(rs.getString(4));
                                tfId.setText(rs.getString(1));
                            } else {
                                AlertGUI alertGUI = new AlertGUI();
                                alertGUI.showAlertMessage("Login", "Citizen doesnt exist!");
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
            }
        });

        bSubmit.setOnAction(new EventHandler <ActionEvent>() {
            public void handle(ActionEvent event) {
                if(checkBox.isSelected()){
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
                    String name = tfName.getText();
                    String surname = tfSurname.getText();
                    try {
                        String sql = "Select * from citizien Where name='" + name + "' and surname='" + surname + "'";
                        stmt = conn.prepareStatement(sql);
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            int hasVoted = rs.getInt("hasVoted");
                            if(hasVoted == 0){
                                UserDB userDB = new UserDB();
                                userDB.updateCitizen(new Citizen(rs.getInt(1),1));
                                AlertGUI alertGUI = new AlertGUI();
                                alertGUI.showAlertMessage("Voting", "Citizen is allowed to vote!");
                                sql = "Select * from already_voted";
                                stmt = conn.prepareStatement(sql);
                                rs = stmt.executeQuery(sql);
                                if (rs.next()) {
                                    userDB.updateAlreadyVoted(new AlreadyVoted(1));
                                }
                            } if(hasVoted > 0) {
                                AlertGUI alertGUI = new AlertGUI();
                                alertGUI.showAlertMessage("Alert", "Citizen has already voted!");
                            }
                        } else {
                            AlertGUI alertGUI = new AlertGUI();
                            alertGUI.showAlertMessage("Alert", "Citizen doesnt exist!");
                        }


                        // You can also validate user by result size if its comes zero user is invalid else user is valid

                    } catch (Exception err) { err.printStackTrace();
                    }
                    if(conn !=null) {
                        try {
                            conn.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                else {
                    AlertGUI alertGUI = new AlertGUI();
                    alertGUI.showAlertMessage("Alert", "The information is not verified!");
                }
            }
        });


        grid.add(hbInfo,0,0);
        grid.add(vBox,0,1);
        grid.add(checkBox,0,2);
        grid .add(bSubmit,0,3);

        primaryStage.setTitle("Home Page");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}