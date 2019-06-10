package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;
import middleware.UserMiddleware;


public class UserController {

    private UserTCPConnection userTCPConnection = new UserTCPConnection();
    private UserQueue userQueue = new UserQueue();

    private String passId;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField passportField;

    @FXML
    private Button submitButton;

    @FXML
    protected void handleSubmitButton(ActionEvent event) throws Exception {

        Window window = submitButton.getScene().getWindow();

        String alertText = "An error occured! Please enter your information properly!";

        if (passportField.getText().isEmpty()) {
            AlertGUI.showAlertMessage("Passport ID Alert", alertText);
        }
        else {
            passId = passportField.getText();
        }

        UserMiddleware userMiddleware = new UserMiddleware();
        boolean isValid = userMiddleware.doesCitizenExist(passId);

        if(isValid) {
            userTCPConnection.sendMessageThroughTCP();
            userQueue.addUserToQueue();
            String vote = userTCPConnection.getMessageThroughTCP();

            if (vote != null) {
                userQueue.removeUserFromQueue();
            }

        } else {
            AlertGUI.showAlertMessage("Citizien Alert","This citizien is not part of this unit!");
        }

        if (fullNameField.getText().isEmpty()) {
            AlertGUI.showAlertMessage("Name or Surname Incorrect", alertText);
        }
    }

    public String getPassId() {
            return this.passId;
    }

}
