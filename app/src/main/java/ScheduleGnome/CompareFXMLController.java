package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class CompareFXMLController {
    @FXML
    GridPane calGrid1;
    @FXML
    GridPane calGrid2;
    private Label[][] label = new Label[6][9];
    private Label[][] label2 = new Label[6][9];
    @FXML
    ObservableList<LocalTime> startTimeList;
    @FXML
    ChoiceBox<String> schedule1;
    @FXML
    ChoiceBox<String> schedule2;
    @FXML
    private ObservableList<String> savedNames = FXCollections.observableArrayList();


    public void initialize() {

            //setting up choice boxes
            savedNames.addAll(JavaFXApp.getCurrentUser().savedSchedules.keySet());
             schedule1.setItems(savedNames);
             schedule2.setItems(savedNames);


            //setting up schedule 1
            startTimeList = FXCollections.observableArrayList();
            startTimeList.add(null);
            for (int i = 8; i < 16; i++) {
                startTimeList.add(LocalTime.of(i, 0));
            }

            for (int i = 0; i < 1; i++) {
                for (int j = 1; j < label2[i].length; j++) {
                    if (j > calGrid2.getRowCount() - 1 && calGrid2.getRowCount() > 0) {
                        calGrid2.addRow(calGrid2.getRowCount());
                    }
                    label2[i][j] = new Label();
                    label2[i][j].setText(startTimeList.get(j).toString());
                    calGrid1.add(label2[i][j], i, j);
                }
            }

            //setting up schedule 2
            for (int i = 0; i < 1; i++) {
                for (int j = 1; j < label[i].length; j++) {
                    if (j > calGrid2.getRowCount() - 1 && calGrid2.getRowCount() > 0) {
                        calGrid2.addRow(calGrid2.getRowCount());
                        RowConstraints row = new RowConstraints();
                       row.setMaxHeight(calGrid2.getRowCount());
                    }
                    label[i][j] = new Label();
                    label[i][j].setText(startTimeList.get(j).toString());
                    calGrid2.add(label[i][j], i, j);
                }

            }


        }

        @FXML
        public void updateFirstCal(ActionEvent actionEvent){
           Schedule schedule = JavaFXApp.getCurrentUser().savedSchedules.get(schedule1.getValue()) ;

            int row;
            ArrayList<Integer> classes = new ArrayList<>();

            if(schedule.scheduleSize()>0) {
                for (Event e : schedule.getEvents()) {
                    row = e.getStartTime().getHour();

                    //determines which columns the course should be in
                    if (e.getDatesString().contains("M")) {
                        classes.add(1);
                    }
                    if (e.getDatesString().contains("T")) {
                        classes.add(2);
                    }
                    if (e.getDatesString().contains("W")) {
                        classes.add(3);
                    }
                    if (e.getDatesString().contains("R")) {
                        classes.add(4);
                    }
                    if (e.getDatesString().contains("F")) {
                        classes.add(5);
                    }
                    //creates labels for the added course
                    for (Integer aClass : classes) {
                        label[aClass][row - 7] = new Label();
                        label[aClass][row - 7].setText(e.getTitle());
                        label[aClass][row - 7].setWrapText(true);

                        calGrid1.add(label[aClass][row - 7], aClass, row - 7);
                    }
                    classes.clear();
                }
            }
        }
    @FXML
    public void updateSecondCal(ActionEvent actionEvent){
        Schedule schedule = JavaFXApp.getCurrentUser().savedSchedules.get(schedule2.getValue()) ;

        int row;
        ArrayList<Integer> classes = new ArrayList<>();

        if(schedule.scheduleSize()>0) {
            for(Event e: schedule.getEvents()) {
                row = e.getStartTime().getHour();

                //determines which columns the course should be in
                if (e.getDatesString().contains("M")) {
                    classes.add(1);
                }
                if (e.getDatesString().contains("T")) {
                    classes.add(2);
                }
                if (e.getDatesString().contains("W")) {
                    classes.add(3);
                }
                if (e.getDatesString().contains("R")) {
                    classes.add(4);
                }
                if (e.getDatesString().contains("F")) {
                    classes.add(5);
                }
                //creates labels for the added course
                for (Integer aClass : classes) {
                    label2[aClass][row - 7] = new Label();
                    label2[aClass][row - 7].setText(e.getTitle());
                    label2[aClass][row - 7].setWrapText(true);

                    calGrid2.add(label2[aClass][row - 7], aClass, row - 7);
                }
                classes.clear();
            }
        }
    }


    @FXML
        public void clickHome(ActionEvent actionEvent) throws IOException {
            JavaFXApp.changeScene("savedScene.fxml");
        }

//        public void updateCal1(){
//            JavaFXApp.setCurrentSchedule(;
//        }
    }



