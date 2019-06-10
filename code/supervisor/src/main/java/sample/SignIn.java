package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

public class SignIn {

    public SignIn() {
        start();

    }
    private void start(){
        final Stage primaryStage = new Stage();
        primaryStage.setTitle("E-VOTING SUPERVISOR");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1500, 800);
        grid.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));

        Text scenetitle = new Text("");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label lCity = new Label("City");
        grid.add(lCity, 0, 1);

        final TextField tfCity = new TextField();
        grid.add(tfCity, 1, 1);

        final Label lCenterNum = new Label("Center Number:");
        grid.add(lCenterNum, 0, 2);

        final TextField tfCenterNum = new TextField();
        grid.add(tfCenterNum, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);


        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
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
                String city = tfCity.getText();
                String centerNum = tfCenterNum.getText();

                try {
                    if (city != null && centerNum != null) {
                        String sql = "Select * from voting_center Where city='" + city + "' and centerNum='" + Integer.parseInt(centerNum) + "'";
                        stmt = conn.prepareStatement(sql);
                        rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                            SupervisorGUI supervisorGUI = new SupervisorGUI(rs.getInt(1), city, Integer.parseInt(centerNum));
                            primaryStage.close();
                        } else {
                            AlertGUI alertGUI = new AlertGUI();
                            alertGUI.showAlertMessage("Login", "Voting Center doesn't exist");
                        }
                    }else {
                        AlertGUI alertGUI = new AlertGUI();
                        alertGUI.showAlertMessage("Login", "Please don't leave the fields empty");
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

        grid.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));


        primaryStage.setTitle("Home Page");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
