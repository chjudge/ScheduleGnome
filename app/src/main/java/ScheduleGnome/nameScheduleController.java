package ScheduleGnome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class nameScheduleController implements Initializable {
    private String name;
    @FXML
    TextField textField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": Loading name schedule page");
    }

    public void create(ActionEvent actionEvent) throws IOException {
        name = textField.getText();
        JavaFXApp.getCurrentUser().addNewSchedule(name);
        JavaFXApp.setCurrentSchedule(new Schedule("" + java.lang.Math.random()));
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": "+JavaFXApp.getCurrentUser().getUsername()+" created "+
                " a new schedule named "+name);
        JavaFXApp.changeScene("searchScheduleScene.fxml");
        Pane pane = new Pane();

    }

    public void cancel(ActionEvent actionEvent) throws IOException{
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": "+JavaFXApp.getCurrentUser().getUsername()+" cancelled making" +
                "a new schedule");
        JavaFXApp.changeScene("savedScene.fxml");
    }

}
