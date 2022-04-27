package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalTime;

public class addOwnEventController {

    @FXML
    TextField title;
    @FXML
    ObservableList<LocalTime> startTimeList;
    @FXML
    ObservableList<LocalTime> endTimeList;
    @FXML
    ComboBox<LocalTime> startTimeChoice;
    @FXML
    ComboBox<LocalTime> endTimeChoice;
    String eTitle;

    @FXML
    CheckBox monday;
    @FXML
    CheckBox tuesday;
    @FXML
    CheckBox wednesday;
    @FXML
    CheckBox thursday;
    @FXML
    CheckBox friday;

    @FXML private Text eventactiontarget;


    //add event to schedule
    public void add(ActionEvent actionEvent) throws IOException{
        if (title.getText().isBlank()) {
            eventactiontarget.setText("Enter an event name.");
            return;
        }
        //Check for duplicate extracurriculars
        String titleText = title.getText().toUpperCase();
        boolean duplicate = false;
        for(Event event : JavaFXApp.getCurrentSchedule().getEvents()) {
            if(event instanceof Extracurriculars) {
                if(event.getTitle().equals(titleText)) {
                    duplicate=true;
                }
            }
        }
        if(duplicate) {
            eventactiontarget.setText("Event already exists.");
            return;
        }
        if(!monday.isSelected() && !tuesday.isSelected() && !wednesday.isSelected() &&
                !thursday.isSelected() && !friday.isSelected()) {
            eventactiontarget.setText("Select days of event.");
            return;
        }

        if(startTimeChoice.getValue() == null || endTimeChoice.getValue() == null) {
            eventactiontarget.setText("Set start and end time of event.");
            return;
        }

        if(startTimeChoice.getValue().isAfter(endTimeChoice.getValue()) ||
            startTimeChoice.getValue().equals(endTimeChoice.getValue())) {
            eventactiontarget.setText("Choose a valid start and end time of event.");
            return;
        }

        //Add fields to Event
        String dates="";
        if(monday.isSelected()) {
            dates += "M";
        }
        if(tuesday.isSelected()) {
            dates += "T";
        }
        if(wednesday.isSelected()) {
            dates += "W";
        }
        if(thursday.isSelected()) {
            dates += "R";
        }
        if(friday.isSelected()) {
            dates += "F";
        }
        Extracurriculars exc = new Extracurriculars(titleText, startTimeChoice.getValue(),
                endTimeChoice.getValue(), dates);
        if(JavaFXApp.getCurrentSchedule().hasConflicts(exc) != null) {
            eventactiontarget.setText("Extracurricular conflicts with existing event.");
            return;
        }
        JavaFXApp.getCurrentSchedule().addEvent(exc);
        JavaFXApp.changeScene("searchScheduleScene.fxml");

    }

    public void back(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("searchScheduleScene.fxml");
    }

    public void initialize(){
        startTimeList = FXCollections.observableArrayList();
        endTimeList = FXCollections.observableArrayList();

        startTimeList.add(null);
        endTimeList.add(null);

        for (int i = 8; i < 21; i++) {
            startTimeList.add(LocalTime.of(i, 0));
            endTimeList.add(LocalTime.of(i, 0));
        }

        startTimeChoice.setItems(startTimeList);
        endTimeChoice.setItems(endTimeList);


    }
}
