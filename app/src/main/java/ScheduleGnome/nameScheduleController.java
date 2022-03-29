package ScheduleGnome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class nameScheduleController implements Initializable {
    private String name;
    @FXML
    TextField textField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void create(ActionEvent actionEvent) throws IOException {
        name = textField.getText();
        JavaFXApp.getCurrentUser().addNewSchedule(name);
        JavaFXApp.setCurrentSchedule(new Schedule("" + java.lang.Math.random()));

        JavaFXApp.changeScene("searchScheduleScene.fxml");
        Pane pane = new Pane();

    }

    public void cancel(ActionEvent actionEvent) throws IOException{
        JavaFXApp.changeScene("savedScene.fxml");
    }

}
