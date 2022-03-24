package ScheduleGnome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class loginFXMLController {

    @FXML private Text actiontarget;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    private String usersFileName = "users.txt";
    
    @FXML protected void loginButton(ActionEvent event) throws IOException {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) return;

        //I should add all existing users from the usersFile to JavaFXApp.addUser i think

        
        int response = JavaFXApp.login(usernameField.getText(), passwordField.getText());
        switch (response) {
            case -1:
                actiontarget.setText("Sorry, there is no user named " + usernameField.getText());
                break;
            case 0:
                actiontarget.setText("Sorry, your password is incorrect");
                break;
            case 1:
                actiontarget.setText("Logged in as " + JavaFXApp.getCurrentUser().getUsername());
                JavaFXApp.changeScene("savedScene.fxml");
//                JavaFXApp.setScene("select");
                //actiontarget.setText("Logged in as " + JavaFXApp.getCurrentUser().getUsername());
                break;
            default:
                break;
        }
    }

    @FXML protected void registerButton(ActionEvent event) throws IOException {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) return;

        //Storing passwords with plain text encryption algorithm
        String newUsername = usernameField.getText();
        String newPassword = passwordField.getText();

        if(registerUser(newUsername, newPassword)) {
            User newUser = new User(newUsername, newPassword);
            JavaFXApp.addUser(newUser);
            JavaFXApp.changeScene("savedScene.fxml");
        }
        else {
            //Is this how I do this?
            //How do i display the message from earlier
            actiontarget.setText("Registration failed.");
        }

        
//        JavaFXApp.setScene("select");

        //actiontarget.setText("Logged in as " + JavaFXApp.getCurrentUser().getUsername());
    }

    /**
     * Registers the new user in the users file
     * @param username username of the new user
     * @param password password of the new user
     * @return true on success, false on error
     */
    protected boolean registerUser(String username, String password) {
        try {
            File usersFile = new File(usersFileName);
            FileWriter fw = new FileWriter(usersFile);

            if(username.contains(":")) {
                fw.close();
                throw new Exception("Username cannot contain the character \':\'");
            }

            fw.write(username + ":" + password + "\n");
            fw.flush();
            fw.close();
            return true;
        }
        catch (Exception e) {
            //Log the exception or display it in javafx app
            return false;
        }
    }

}