package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class loginFXMLController {

    @FXML private Text actiontarget;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private ArrayList<User> users = new ArrayList<User>();

    @FXML public void initialize() {

        users.addAll(JavaFXApp.getDB().getUsers());
        for(User user : users) {
            JavaFXApp.addUser(user);
        }

    }
    
    @FXML protected void loginButton(ActionEvent event) throws IOException {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) return;

        int response = JavaFXApp.login(usernameField.getText().toUpperCase(), passwordField.getText());
        switch (response) {
            case -1:
                actiontarget.setText("Sorry, there is no user named " + usernameField.getText());
                break;
            case 0:
                actiontarget.setText("Sorry, your password is incorrect");
                break;
            case 1:
                actiontarget.setText("Logged in as " + JavaFXApp.getCurrentUser().getUsername());
                loadUserSchedules(JavaFXApp.getCurrentUser());
                JavaFXApp.changeScene("savedScene.fxml");
                break;
            default:
                break;
        }
    }

    @FXML protected void registerButton(ActionEvent event) throws IOException {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) return;

        //Storing passwords with plain text encryption algorithm
        String newUsername = usernameField.getText().toUpperCase();
        String newPassword = passwordField.getText();

        User newUser = registerUser(newUsername, newPassword);
        if(newUser != null) {
            users.add(newUser);
            JavaFXApp.addUser(newUser);
            if(JavaFXApp.login(newUsername, newPassword) == 1) {
                System.out.println("Current user:" + JavaFXApp.getCurrentUser().getId());
                System.out.println("Current user:" + JavaFXApp.getCurrentUser().getUsername());
            }
            JavaFXApp.changeScene("savedScene.fxml");
        }

    }

    protected User registerUser(String username, String password) {
        User user = new User(username, password);
        if(JavaFXApp.getDB().insertUser(user)) //TODO: add graduation_year and major
        {
            return user;
        }
        return null;

    }

    protected void loadUserSchedules(User user) {
        JavaFXApp.getDB().loadSchedules(user);
        System.out.println("Printing user schedule:\n---------\n");
        for(Schedule schedule : JavaFXApp.getCurrentUser().getSavedSchedules().values()) {
            System.out.println(schedule.getName());
            for(Event event : schedule.events) {
                System.out.println(event.getTitle());
            }
            System.out.println();
        }
    }

    /**
     * Registers the new user in the users file
     * Logins are delimited by a ':' symbol and end in a new line in the users file
     * Usernames are not case sensitive, passwords are
     * Ex: USERNAME:password
     * @param username username of the new user
     * @param password password of the new user
     * @return true on success, false on error
     */
//    protected boolean registerUser(String username, String password) {
//
//
//        try {
//            File usersFile = new File(usersFileName);
//            FileWriter usersFileWriter;
//            if(usersFile.exists()) {
//                usersFileWriter = new FileWriter(usersFile,true);
//            }
//            else {
//                usersFileWriter = new FileWriter(usersFile);
//            }
//
//            if(username.contains(":")) {
//                usersFileWriter.close();
//                throw new Exception("Username cannot contain the character \':\'");
//            }
//            else if(username.isBlank()) {
//                usersFileWriter.close();
//                throw new Exception("Username cannot be blank.");
//            }
//            if(password.contains(":")) {
//                usersFileWriter.close();
//                throw new Exception("Password cannot contain the character \':\'");
//            }
//            else if(password.isBlank()) {
//                usersFileWriter.close();
//                throw new Exception("Password cannot be blank.");
//            }
//
//            for(User user : users) {
//                if(user.getUsername().equals(username.toUpperCase())) {
//                    usersFileWriter.close();
//                    throw new Exception("Username taken.");
//                }
//            }
//
//            //Store usernames in uppercase so they aren't case sensitive
//            usersFileWriter.write(username.toUpperCase() + ":" + password + "\n");
//            usersFileWriter.flush();
//            usersFileWriter.close();
//
//            //Create a file for the new user to store their saved schedules
//            File savedScheduleFile = new File(username + ".txt");
//            FileWriter scheduleFileWriter = new FileWriter(savedScheduleFile);
//            scheduleFileWriter.close();
//
//            return true;
//        }
//        catch (Exception e) {
//            //Log the exception or display it in javafx app
//            actiontarget.setText("Registration failed. " + e.getMessage());
//            return false;
//        }
//    }
//
//    protected void readAllUsers() {
//
//        File usersFile = new File(usersFileName);
//        Scanner scanner;
//        try {
//            scanner = new Scanner(usersFile);
//        }
//        catch(IOException ioe) {
//            actiontarget.setText("Error loading all users.");
//            return;
//        }
//
//        while(scanner.hasNextLine()) {
//            String userAndPassString = scanner.nextLine();
//            if (userAndPassString.isBlank()) {
//                return; //An empty line in the file
//            }
//            String[] userAndPassArray = userAndPassString.split(":");
//
//            if (userAndPassArray.length > 2) {
//                actiontarget.setText("Error loading all users.");
//                return;
//            }
//            User newUser = new User(userAndPassArray[0], userAndPassArray[1]);
//            users.add(newUser);
//
//            JavaFXApp.addUser(newUser);
//        }
//    }

}