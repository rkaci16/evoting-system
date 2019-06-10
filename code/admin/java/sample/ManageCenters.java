package sample;

import com.google.protobuf.StringValue;
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
import model.VotingCenter;

import java.sql.*;


public class ManageCenters {
    public ManageCenters(Stage primaryStage, Scene oldScene) {

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
        Label lNO = new Label("NO");
        Label lCity = new Label("City");

        TextField tfID = new TextField();
        TextField tfNO = new TextField();
        ComboBox cbCity = new ComboBox();

        cityCombobox(cbCity);

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
                if(comboBox.getValue().toString().equals("Add New")){
                    bRegister.setDisable(false);
                    bModify.setDisable(true);
                    bRemove.setDisable(true);
                    tfID.setEditable(true);
                    tfID.setText("");
                    tfNO.setText("");
                    cbCity.setValue("");
                }
                else {
                    try {

                        String sql = "Select * from voting_center Where id ='" + comboBox.getValue().toString() + "'";
                        stmt = conn.prepareStatement(sql);
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            bRegister.setDisable(true);
                            bModify.setDisable(false);
                            bRemove.setDisable(false);
                            tfID.setText(String.valueOf(rs.getInt(1)));
                            tfNO.setText(String.valueOf(rs.getString(3)));
                            cbCity.setValue(rs.getString(2));
                        }

                        // You can also validate user by result size if its comes zero user is invalid else user is valid

                    } catch (SQLException err) {
                    }
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
        bModify.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    userDB.updateVotingCenter(new VotingCenter(Integer.parseInt(tfID.getText()), String.valueOf(cbCity.getValue()),Integer.parseInt(tfNO.getText())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bRegister.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    userDB.saveVotingCenter(new VotingCenter(Integer.parseInt(tfID.getText()), String.valueOf(cbCity.getValue()), Integer.parseInt(tfNO.getText())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                comboBox.getItems().add(tfID.getText());
            }
        });

        bRemove.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String temp = tfID.getText();
                try {
                    userDB.deleteVotingCenter(Integer.parseInt(tfID.getText()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                comboBox.getItems().remove(temp);
            }
            });
        gridPane.add(bBack,0,0);
        gridPane.add(comboBox, 0,1);
        gridPane.add(lID,0,2);
        gridPane.add(lNO,0,3);
        gridPane.add(lCity,0,4);
        gridPane.add(tfID,1,2);
        gridPane.add(tfNO,1,3);
        gridPane.add(cbCity,1,4);
        gridPane.add(bRegister, 0,5);
        gridPane.add(bModify, 1,5);
        gridPane.add(bRemove, 2,5);
        gridPane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(gridPane, 1500, 800);


        primaryStage.setTitle("Manage Voting Centers");
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
            String sql = "Select id from voting_center";
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
    public void cityCombobox(ComboBox comboBox){
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
            String sql = "Select name from city";
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
