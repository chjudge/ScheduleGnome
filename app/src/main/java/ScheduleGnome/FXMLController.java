package ScheduleGnome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class FXMLController {

    @FXML private Text actiontarget;
    
    @FXML protected void loginButton(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }
}