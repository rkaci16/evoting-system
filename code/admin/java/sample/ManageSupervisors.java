package sample;

import db.UserDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Supervisor;

import java.sql.*;

public class ManageSupervisors {
    public ManageSupervisors(Stage primaryStage, Scene oldScene) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
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

        Label lID = new Label("ID");
        Label lName = new Label("Full Name");
        Label lVotingCenter = new Label("Voting Center");

        TextField tfID = new TextField();
        TextField tfName = new TextField();
        TextField tfVotingCenter = new TextField();

        Button bRemove = new Button("REMOVE");
        Button bModify = new Button("MODIFY");
        Button bRegister = new Button("REGISTER");

        ComboBox comboBox = new ComboBox();
        String addNew = new String("Add New");
        comboBox.getItems().add(addNew);
        populateCombobox(comboBox);
        tfID.setEditable(false);
        comboBox.setOnAction(new EventHandler <ActionEvent>() {
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
                    String sql = "Select * from supervisor Where full_name='" + comboBox.getValue().toString() + "'";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery(sql);
                    if(comboBox.getValue().toString().equals("Add New")){
                        bRegister.setDisable(false);
                        bModify.setDisable(true);
                        bRemove.setDisable(true);
                        tfID.setEditable(true);
                        tfID.setText("");
                        tfName.setText("");
                        tfVotingCenter.setText("");
                    }else {
                        if (rs.next()) {
                            bRegister.setDisable(true);
                            bModify.setDisable(false);
                            bRemove.setDisable(false);
                            tfID.setText(String.valueOf(rs.getInt(1)));
                            tfName.setText(rs.getString(2));
                            tfVotingCenter.setText(rs.getString(3));
                        }

                        // You can also validate user by result size if its comes zero user is invalid else user is valid
                    }
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

        UserDB userDB = new UserDB();
        bRemove.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String temp = tfName.getText();
                try {
                    userDB.deleteSupervisor(Integer.parseInt(tfID.getText()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                comboBox.getItems().remove(temp);
            }
        });

        bModify.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    userDB.updateSupervisor(new Supervisor(Integer.parseInt(tfID.getText()), tfName.getText(),Integer.parseInt(tfVotingCenter.getText())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bRegister.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    userDB.saveSupervisor(new Supervisor(Integer.parseInt(tfID.getText()), tfName.getText(),Integer.parseInt(tfVotingCenter.getText())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                comboBox.getItems().add(tfName.getText());
            }
        });


        gridPane.add(bBack,0,0);
        gridPane.add(comboBox, 0,1);
        gridPane.add(lID,0,2);
        gridPane.add(lName,0,3);
        gridPane.add(lVotingCenter,0,4);
        gridPane.add(tfID,1,2);
        gridPane.add(tfName,1,3);
        gridPane.add(tfVotingCenter,1,4);
        gridPane.add(bRegister, 0,5);
        gridPane.add(bModify, 1,5);
        gridPane.add(bRemove, 2,5);
        gridPane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(gridPane, 1500, 800);


        primaryStage.setTitle("Manage Supervisors");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void populateCombobox(ComboBox comboBox){
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
            String sql = "Select full_Name from supervisor";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                comboBox.getItems().addAll(rs.getString(1));
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
