package ScheduleGnome;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class JavaFXApp extends Application {

    private static Map<String, User> users;
    private static User currentUser;
    private static Schedule currentSchedule;
    private static Stage stg;
    public static DateTimeFormatter dtf;
    public static boolean isLogging;

    private static DBOperator db;

    @Override
    public void start(Stage stage) throws Exception {
        stg = stage;
        Parent root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));

        Scene mainScene = new Scene(root, 800, 450);
        mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Log("Launched main scene");

        stage.setTitle("ScheduleGnome | Login");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.show();

    }

    public static void changeScene(String fxml) throws IOException {
        stg.setTitle("ScheduleGnome" + " | " + (currentUser == null ? "Login" : currentUser.getUsername()));
        Parent pane = FXMLLoader.load(JavaFXApp.class.getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void changeSceneAndTitle(String fxml, String title) throws IOException {

        stg.setTitle(stg.getTitle() + " | " + title);
        Log(currentUser.getUsername()+" changed scene to "+fxml);
        Parent pane = FXMLLoader.load(JavaFXApp.class.getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        isLogging = args.length > 1 && args[1].equals("logger");
        isLogging = true;
        users = new HashMap<String, User>();
        db = new DBOperator();
        Log("Launched");
        launch(args);
    }

    public static int login(String username, String password) {
        if (users.get(username) != null) {
            if (users.get(username).checkPassword(password)) {
                currentUser = users.get(username);
                Log(username+" successfully logged in");
                return 1;
            }
            Log("+"+username+" typed in wrong password");
            return 0;
        }
        Log(username+" was not in the database");
        return -1;
    }

    // TODO: make sure you can't overwrite users
    public static void addUser(User user) {
        users.put(user.getUsername(), user);
        currentUser = user;
        // TODO: print log if user is added
        Log("New user \""+user+"\" was added to the database");
    }

    public static void logout() {
        currentUser = null;
        Log("Logged out");
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

    public static DBOperator getDB(){
        if(db.checkConnection()) {
            return db;
        } else {
            System.out.println("DB connection failed");
                db.close();

            db = new DBOperator();
            return db;
        }
    }

    public static void Log(String s) {
        if (JavaFXApp.isLogging)
            System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+ ": " + s);
    }
}