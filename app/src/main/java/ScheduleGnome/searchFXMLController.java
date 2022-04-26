package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import org.controlsfx.control.textfield.TextFields;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class searchFXMLController {
    @FXML
    TextField searchField;
    @FXML
    ComboBox<String> departmentChoice;
    @FXML
    ComboBox<LocalTime> startTimeChoice;
    @FXML
    ComboBox<LocalTime> endTimeChoice;
    @FXML
    ListView<SearchResult> searchList;
    @FXML
    ListView<CalendarEvent> eventList;
    @FXML
    Button backButton;

    @FXML
    ObservableList<String> departmentList;
    @FXML
    ObservableList<LocalTime> startTimeList;
    @FXML
    ObservableList<LocalTime> endTimeList;
    @FXML
    ObservableList<SearchResult> searchResultList;
    @FXML
    GridPane calGrid;
    private Label[][] label = new Label[6][9];


    @FXML
    static ObservableList<CalendarEvent> calendarEventList; // TODO Need to make some for MTWRF

    Search search;

    private String savedSchedulePath = "./src/main/savedSchedules";

    @FXML
    protected void search(ActionEvent event) {
        String searched = searchField.getText();
        //if (searched.isBlank()) return;
        System.out.println(searched);


        searchResultList.clear();

        //System.out.println("cleared list");

        search.setSearched(searched);
        if (departmentChoice.getValue() != null)
            search.setDept(departmentChoice.getValue());
        if (startTimeChoice.getValue() != null)
            search.setStartTime(startTimeChoice.getValue().toString());
        if (endTimeChoice.getValue() != null)
            search.setEndTime(endTimeChoice.getValue().toString());

        System.out.println("search query: " + searched);

        ArrayList<Course> results = search.querySearch();
        System.out.println(results.size() + " results");

        for (Course course : results) {
            searchResultList.add(new SearchResult(course, this));
        }

        // for (Course course : searchResultList) {
        //     System.out.println(course);
        // }


        searchList.setItems(searchResultList);
    }

    public void initialize() {
        JavaFXApp.Log("Loading search!");
        search = new Search(JavaFXApp.getCurrentSchedule().isFall());
        searchResultList = FXCollections.observableArrayList();

        departmentList = FXCollections.observableArrayList();
        departmentList.addAll(JavaFXApp.getDB().getDistinctDepts());
        departmentList.sort(String.CASE_INSENSITIVE_ORDER);

        departmentList.add(0, null);

        System.out.println(departmentList.toString());

        departmentChoice.setItems(departmentList);



        startTimeList = FXCollections.observableArrayList();
        endTimeList = FXCollections.observableArrayList();

        startTimeList.add(null);
        endTimeList.add(null);

        for (int i = 8; i < 20; i++) {
            startTimeList.add(LocalTime.of(i, 0));
            endTimeList.add(LocalTime.of(i, 0));
        }

        startTimeChoice.setItems(startTimeList);
        endTimeChoice.setItems(endTimeList);

        calendarEventList = FXCollections.observableArrayList();

        for(int i = 0; i<1; i++){
            for(int j = 1; j<label[i].length; j++){
                label[i][j] = new Label();
                label[i][j].setText(startTimeList.get(j).toString());
                calGrid.add(label[i][j], i, j);
            }
        }
        ArrayList<String> titles = JavaFXApp.getDB().getAllTitles(JavaFXApp.getCurrentSchedule().isFall());

        //titles.addAll(departmentList);

        TextFields.bindAutoCompletion(searchField,titles);
        updateCalendar();
    }



    public void updateCalendar() {
        // TODO: Fill this with events
        JavaFXApp.Log("Updating " + JavaFXApp.getCurrentUser().getUsername()+ "'s "+
                JavaFXApp.getCurrentSchedule().getName()+" calendar");
        calendarEventList.clear();
        int row;
        ArrayList<Integer> classes = new ArrayList<>();
        calGrid.getChildren().clear();
        calGrid.setGridLinesVisible(true);
        for(int i = 0; i<1; i++){
            for(int j = 1; j<label[i].length; j++){
                label[i][j] = new Label();
                label[i][j].setText(startTimeList.get(j).toString());
                calGrid.add(label[i][j], i, j);
            }
        }
        calGrid.add(new Label("Monday"), 1, 0);
        calGrid.add(new Label("Tuesday"), 2, 0);
        calGrid.add(new Label("Wednesday"), 3, 0);
        calGrid.add(new Label("Thursday"), 4, 0);
        calGrid.add(new Label("Friday"), 5, 0);

        for(int i = 0; i < JavaFXApp.getCurrentSchedule().scheduleSize(); i++){
            Event e  = JavaFXApp.getCurrentSchedule().getEvents().get(i);
            System.out.println(e.getTitle());
            row = e.getStartTime().getHour();

            //determines which columns the course should be in
            if(e.getDatesString().contains("M")){
                classes.add(1);
            }if(e.getDatesString().contains("T")){
                classes.add(2);
            }if(e.getDatesString().contains("W")){
                classes.add(3);
            }if(e.getDatesString().contains("R")){
                classes.add(4);
            }if(e.getDatesString().contains("F")){
                classes.add(5);
            }
            //creates labels for the added course
            for (Integer aClass : classes) {
                label[aClass][row - 7] = new Label();
                label[aClass][row - 7].setText(e.getTitle());
                label[aClass][row - 7].setWrapText(true);

                calGrid.add(label[aClass][row - 7], aClass, row - 7);
            }
            classes.clear();
        }
    }

    public void back() throws IOException {

        JavaFXApp.Log(JavaFXApp.getCurrentUser().getUsername()+" hit the back button");
        saveSchedule();
        JavaFXApp.Log(JavaFXApp.getCurrentUser().getUsername()+" saved their "+
                JavaFXApp.getCurrentSchedule().getName()+" schedule");
        JavaFXApp.changeScene("savedScene.fxml");
    }

    private void saveSchedule() {
        String currUsername = JavaFXApp.getCurrentUser().getUsername();
        JavaFXApp.Log("Saving "+currUsername+"'s "
                +JavaFXApp.getCurrentSchedule().getName()+" schedule");
        //Save courses to schedule
        JavaFXApp.getDB().saveSchedule(JavaFXApp.getCurrentSchedule());


    }
}

class SearchResult extends HBox {
    Course course;
    Label courseLabel;
    Label errorLabel;
    searchFXMLController controller;

    public SearchResult(Course course, searchFXMLController controller) {
        super();
        this.course = course;
        this.controller = controller;
        courseLabel = new Label(course.toString());
        errorLabel = new Label();
        errorLabel.setStyle("-fx-font: 12 monospace;");
        courseLabel.setStyle("-fx-font: 12 monospace;");
        Event conflict = JavaFXApp.getCurrentSchedule().hasConflicts(course);
        if (conflict==null) {
            Button addButton = new Button("+");
            addButton.setOnAction((ActionEvent e) -> {
                JavaFXApp.getCurrentSchedule().addEvent(course);
                controller.updateCalendar();
                controller.search(e);
            });
            this.getChildren().addAll(courseLabel, addButton);
        }
        else {
            try {
                Course crsConflict = (Course)conflict;
                System.out.println("CONFLICT: " + crsConflict.getCourseCode() + " CRS: " + course.getCourseCode());
                if (crsConflict.getCourseCode().equals(course.getCourseCode())) {
                    errorLabel.setText("Already in schedule");
                    this.getChildren().addAll(courseLabel, errorLabel);
                }
                else {
                    Button swapButton = new Button("Swap");
                    swapButton.setOnAction((ActionEvent e) -> {
                        JavaFXApp.getCurrentSchedule().deleteEvent(crsConflict);
                        JavaFXApp.getCurrentSchedule().addEvent(course);
                        controller.updateCalendar();
                        controller.search(e);
                    });
                    String conflictCode = crsConflict.getCourseCode();
                    String crsCode = course.getCourseCode();
                    if (conflictCode.substring(0,conflictCode.length()-2).equals(crsCode.substring(0,crsCode.length()-2)))
                        errorLabel.setText("  SECTION CONFLICT:\n  "+crsConflict.getCourseCode());
                    else
                        errorLabel.setText("  TIME CONFLICT:\n  "+crsConflict.getCourseCode());
                    this.getChildren().addAll(courseLabel, swapButton, errorLabel);
                }
            } catch (ClassCastException exc) { // extracurricular
                errorLabel.setText("  EXTRACURRICULAR:  \n"+conflict.getTitle());
                this.getChildren().addAll(courseLabel, errorLabel);
            }
        }
    }
}

class CalendarEvent extends HBox {
    Event event;
    Label eventLabel;
    Button removeButton;
    searchFXMLController controller;

    public CalendarEvent(Event event, searchFXMLController controller) {
        super();
        this.event = event;
        this.controller = controller;
        eventLabel = new Label(event.getTitle()); // TODO: For now
        removeButton = new Button("x");
        removeButton.setOnAction((ActionEvent e) -> {
            JavaFXApp.getCurrentSchedule().deleteEvent(event);
            controller.updateCalendar();
        });

        this.getChildren().addAll(eventLabel, removeButton);
    }

    public Event getEvent() {
        return event;
    }
}