package ScheduleGnome;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class JavaFXApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
        
        Scene loginScene = new Scene(root, 1000, 600);
        loginScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        
        Search searchTool = new Search();
        

        stage.setTitle("ScheduleGnome");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}