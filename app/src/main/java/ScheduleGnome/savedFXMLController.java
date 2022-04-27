package ScheduleGnome;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
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

        for (Schedule schedule : JavaFXApp.getCurrentUser().savedSchedules.values()) {
            scheduleTilePane.getChildren().add(new ScheduleBox(schedule, this));
        }

    }

    public void compareSchedules(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("compareSchedules.fxml");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("loginScene.fxml");
    }

    public TilePane getTilePane() {
        return scheduleTilePane;
    }
}

class ScheduleBox extends AnchorPane {
    Schedule schedule;
    Label scheduleLabel;
    Label semesterLabel;
    Label creditsLabel;
    Button removeButton;

    public ScheduleBox(Schedule schedule, savedFXMLController controller) {
        super();
        this.schedule = schedule;
        scheduleLabel = new Label(schedule.getName()); // TODO: For now
        scheduleLabel.setMaxWidth(100);
        scheduleLabel.setTextFill(Color.WHITE);
        scheduleLabel.setStyle("-fx-font-weight: bold");

        semesterLabel = new Label(schedule.getSemester());
        semesterLabel.setTextFill(Color.WHITE);

        creditsLabel = new Label(schedule.totalCredits() + " credits");
        creditsLabel.setTextFill(Color.WHITE);

        removeButton = new Button("x");
        removeButton.setId("btn-close");
        //removeButton.setStyle("-fx-border-color: transparent;-fx-border-width: 0;-fx-background-color: transparent;");
        removeButton.setOnAction((ActionEvent e) -> {
            JavaFXApp.Log("Removing schedule: " + schedule.getName());
            JavaFXApp.getCurrentUser().getSavedSchedules().remove(schedule.getName());
            JavaFXApp.getDB().deleteSchedule(schedule);
            controller.getTilePane().getChildren().remove(this);
        });

        this.setOnMouseClicked((MouseEvent e) -> {
            JavaFXApp.setCurrentSchedule(schedule);
            try {
                JavaFXApp.changeScene("searchScheduleScene.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
                JavaFXApp.Log("Error changing scene");
            }
        });


        //button top right
        AnchorPane.setRightAnchor(removeButton, -6.0);
        AnchorPane.setTopAnchor(removeButton, -6.0);

        //schedule label bottom left
        AnchorPane.setLeftAnchor(scheduleLabel, 3.0);
        AnchorPane.setBottomAnchor(scheduleLabel, 3.0);

        //semester label center left
        AnchorPane.setLeftAnchor(semesterLabel, 3.0);
        AnchorPane.setTopAnchor(semesterLabel, 20.0);

        //credits label top left
        AnchorPane.setTopAnchor(creditsLabel, 3.0);
        AnchorPane.setLeftAnchor(creditsLabel, 3.0);

        this.setStyle("-fx-background-radius: 10;-fx-background-color: #48634f;-fx-background-insets: 0, 0 1 1 0; " +
                "-fx-border-width: 2px; -fx-border-color: #5c8065; -fx-border-radius: 10");
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStyle("-fx-background-radius: 10;-fx-background-insets: 0, 0 1 1 0; " +
                        "-fx-border-width: 2px; -fx-border-color: #5c8065; -fx-border-radius: 10; -fx-background-color: #5c8065");
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                setStyle("-fx-background-radius: 10;-fx-background-color: #48634f;-fx-background-insets: 0, 0 1 1 0; " +
                        "-fx-border-width: 2px; -fx-border-color: #5c8065; -fx-border-radius: 10");
            }
        });

        this.getChildren().addAll(scheduleLabel, semesterLabel, creditsLabel, removeButton);


    }
}