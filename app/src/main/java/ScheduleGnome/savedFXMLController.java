package ScheduleGnome;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class savedFXMLController implements Initializable {


    @FXML
    private ListView<String> listView;
    @FXML
    private ArrayList<String> savedNames;


    @FXML
    public void clickNew(ActionEvent actionEvent) throws IOException{
        JavaFXApp.changeScene("nameSchedule.fxml");
    }



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        savedNames = new ArrayList<>();
        savedNames.addAll(JavaFXApp.getCurrentUser().savedSchedules.keySet());
        listView.getItems().addAll(savedNames);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    JavaFXApp.changeScene("searchScheduleScene.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }





    public void logout(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("loginScene.fxml");

    }





}
