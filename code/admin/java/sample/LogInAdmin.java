package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;


public class LogInAdmin {
    public LogInAdmin() {start();}


    private void start() {

        final Stage primaryStage = new Stage();
        primaryStage.setTitle("E-VOTING ADMINISTRATOR");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


            Text scenetitle = new Text("");
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);

            Label userName = new Label("User Name:");
            grid.add(userName, 0, 1);

            final TextField userTextField = new TextField();
            grid.add(userTextField, 1, 1);

            Label pw = new Label("Password:");
            grid.add(pw, 0, 2);

            final PasswordField pwBox = new PasswordField();
            grid.add(pwBox, 1, 2);

            Button btn = new Button("Sign in");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 4);


            final Text actiontarget = new Text();
            grid.add(actiontarget, 1, 6);
            //TODO event handler for login verification
            btn.setOnAction(new EventHandler <ActionEvent>() {
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
                                    String username = userTextField.getText();
                                    String password = pwBox.getText();
                                        try {
                                            if (username != null && password != null) {
                                                String sql = "Select * from admin Where username='" + username + "' and password='" + password + "'";
                                                stmt = conn.prepareStatement(sql);
                                                rs = stmt.executeQuery(sql);
                                                if (rs.next()) {
                                                    MainAdmin mainAdmin = new MainAdmin();
                                                    primaryStage.close();
                                                } else {
                                                    AlertGUI alertGUI = new AlertGUI();
                                                    alertGUI.showAlertMessage("Login", "Username or password aren't correct!");
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

            grid.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(grid, 1500, 800);
        primaryStage.setScene(scene);
        primaryStage.show();



}
}
