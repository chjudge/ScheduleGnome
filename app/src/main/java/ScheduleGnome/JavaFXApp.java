package ScheduleGnome;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    private static Search searchTool;
    private static ArrayList<User> users;
    private static User currentUser;
    private static Stage stg;

    @Override
    public void start(Stage stage) throws Exception {
        stg = stage;
        Parent root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
        
        Scene loginScene = new Scene(root, 1000, 600);
        loginScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());


        stage.setTitle("ScheduleGnome");
        stage.setScene(loginScene);
        stage.show();

    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }



    public static void main(String[] args) {
        users = new ArrayList<User>();
        launch(args);
    }

    public static int login(String username, String password){
        for (User user : users) {
            if(user.getUsername().equals(username)){
                if(user.checkPassword(password)){
                    currentUser = user;
                    return 1;
                }
                return 0;
            }
        }
        return -1;
    }

    public static void addUser(User user){
        users.add(user);
        currentUser = user;
    }

    public static User getCurrentUser(){
        return currentUser;
    }
}