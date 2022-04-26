package ScheduleGnome;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class savedFXMLController implements Initializable {
    @FXML
    private TilePane scheduleTilePane;
    @FXML
    private ArrayList<String> savedNames;

    @FXML
    public void clickNew(ActionEvent actionEvent) throws IOException{
        JavaFXApp.Log(JavaFXApp.getCurrentUser().getUsername()+" selected to make" +
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
        JavaFXApp.Log("Loading saved schedules page");
        savedNames = new ArrayList<>();
        savedNames.addAll(JavaFXApp.getCurrentUser().savedSchedules.keySet());
        for(String savedName : savedNames) {
            System.out.println(savedName);
        }


        for (Schedule schedule : JavaFXApp.getCurrentUser().savedSchedules.values()) {
            scheduleTilePane.getChildren().add(new ScheduleBox(schedule));
        }

    }

    public void compareSchedules(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("compareSchedules.fxml");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("loginScene.fxml");
    }
}

class ScheduleBox extends AnchorPane {
    Schedule schedule;
    Label scheduleLabel;
    Button removeButton;

    public ScheduleBox(Schedule schedule) {
        super();
        this.schedule = schedule;
        scheduleLabel = new Label(schedule.getName()); // TODO: For now
        scheduleLabel.setMaxWidth(100);

        removeButton = new Button("x");
        removeButton.setStyle("-fx-border-color: transparent;-fx-border-width: 0;-fx-background-color: transparent;");
        removeButton.setOnAction((ActionEvent e) -> {
            JavaFXApp.Log("Removing schedule: " + schedule.getName());
            //JavaFXApp.getCurrentSchedule().deleteEvent(event);
            //controller.updateCalendar();
        });

        //button top right
        AnchorPane.setRightAnchor(removeButton, 1.0);
        AnchorPane.setTopAnchor(removeButton, 1.0);

        //schedule label bottom left
        AnchorPane.setLeftAnchor(scheduleLabel, 1.0);
        AnchorPane.setBottomAnchor(scheduleLabel, 1.0);

        this.setStyle("-fx-background-radius: 10;-fx-background-color: #48634f;-fx-background-insets: 0, 0 1 1 0;");
        this.getChildren().addAll(scheduleLabel, removeButton);
    }
}