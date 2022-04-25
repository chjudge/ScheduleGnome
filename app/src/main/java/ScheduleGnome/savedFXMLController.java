package ScheduleGnome;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class savedFXMLController implements Initializable {


    @FXML
    private ListView<String> listView;
    @FXML
    private ArrayList<String> savedNames;



    @FXML
    public void clickNew(ActionEvent actionEvent) throws IOException{
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": "+JavaFXApp.getCurrentUser().getUsername()+" selected to make" +
                "a new schedule");
        JavaFXApp.changeScene("nameSchedule.fxml");
    }

    @FXML
    public void clickProfile(ActionEvent actionEvent) throws IOException{
        JavaFXApp.changeScene("profileScene.fxml");
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        //Load in schedules
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": Loading saved schedules page");
        savedNames = new ArrayList<>();
        savedNames.addAll(JavaFXApp.getCurrentUser().savedSchedules.keySet());
        for(String savedName : savedNames) {
            System.out.println(savedName);
        }
        listView.getItems().addAll(savedNames);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    JavaFXApp.setCurrentSchedule(JavaFXApp.getCurrentUser().savedSchedules.get(newValue));
                    JavaFXApp.changeScene("searchScheduleScene.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void compareSchedules(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("compareSchedules.fxml");
    }





    public void logout(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("loginScene.fxml");

    }





}
