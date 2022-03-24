package ScheduleGnome;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    //private static HashMap<String, Parent> sceneMap = new HashMap<>();
    private static Scene mainScene;

    private static Search searchTool;
    private static Map<String, User> users;
    private static User currentUser;
    private static Schedule currentSchedule;
    private static Stage stg;

    @Override
    public void start(Stage stage) throws Exception {
        stg = stage;
        Parent root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
        
        // Scene loginScene = new Scene(root, 800, 450);
        //mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        // sceneMap.put("login", FXMLLoader.load(getClass().getResource("loginScene.fxml")));
        // sceneMap.put("saved", FXMLLoader.load(getClass().getResource("savedScene.fxml")));
        // sceneMap.put("search", FXMLLoader.load(getClass().getResource("searchScheduleScene.fxml")));

        mainScene = new Scene(root, 800, 450);
        // loginScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        System.out.println("LOading Scene!");

        stage.setTitle("ScheduleGnome");
        stage.setScene(mainScene);
        stage.show();

    }

    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(JavaFXApp.class.getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        users = new HashMap<String, User>();
        searchTool = new Search();
        launch(args);
    }

    public static int login(String username, String password) {
        if (users.get(username) != null) {
            if (users.get(username).checkPassword(password)) {
                currentUser = users.get(username);
                return 1;
            }
            return 0;
        }

        return -1;
    }

    public static void addUser(User user) {
        users.put(user.getUsername(), user);
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static Schedule getCurrentSchedule(){
        return currentSchedule;
    }

    public static void setCurrentSchedule(Schedule schedule){
        currentSchedule = schedule;
    }

    // public static void setScene(String name){
    //     Parent scene = sceneMap.get(name);
    //     if(scene != null)
    //         mainScene.setRoot(sceneMap.get(name));
    // }

    public static Search getSearch(){
        return searchTool;
    }
}