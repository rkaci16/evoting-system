package sample;

import javafx.scene.control.Alert;

public class AlertGUI {

    public static void showAlertMessage(String title, String text) {

        Alert fail = new Alert((Alert.AlertType.INFORMATION));
        fail.setTitle(title);
        fail.setHeaderText(null);
        fail.setContentText(text);
        fail.showAndWait();
    }
}
