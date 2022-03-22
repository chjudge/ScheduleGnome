package ScheduleGnome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class loginFXMLController {

    @FXML private Text actiontarget;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    
    @FXML protected void loginButton(ActionEvent event) {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) return;
        int response = JavaFXApp.login(usernameField.getText(), passwordField.getText());
        switch (response) {
            case -1:
                actiontarget.setText("Sorry, there is no user named " + usernameField.getText());
                break;
            case 0:
                actiontarget.setText("Sorry, your password is incorrect");
                break;
            case 1:
                JavaFXApp.setScene("select");
                //actiontarget.setText("Logged in as " + JavaFXApp.getCurrentUser().getUsername());
                break;
            default:
                break;
        }
    }

    @FXML protected void registerButton(ActionEvent event){
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) return;

        User newUser = new User(usernameField.getText(), passwordField.getText());
        JavaFXApp.addUser(newUser);

        JavaFXApp.setScene("select");

        //actiontarget.setText("Logged in as " + JavaFXApp.getCurrentUser().getUsername());
    }
}