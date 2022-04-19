package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
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
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": Loading search page");
        search = JavaFXApp.getSearch();
        searchResultList = FXCollections.observableArrayList();


        departmentList = FXCollections.observableArrayList();
        departmentList.addAll(search.getDB().getDistinctDepts());
        departmentList.sort(String.CASE_INSENSITIVE_ORDER);

        departmentList.add(0, null);

        System.out.println(departmentList.toString());

        departmentChoice.setItems(departmentList);



        startTimeList = FXCollections.observableArrayList();
        endTimeList = FXCollections.observableArrayList();

        startTimeList.add(null);
        endTimeList.add(null);

        for (int i = 8; i < 16; i++) {
            startTimeList.add(LocalTime.of(i, 0));
            endTimeList.add(LocalTime.of(i, 0));
        }

        startTimeChoice.setItems(startTimeList);
        endTimeChoice.setItems(endTimeList);

        calendarEventList = FXCollections.observableArrayList();

        for(int i = 0; i<1; i++){//column
            for(int j = 1; j<label[i].length; j++){//row
                label[i][j] = new Label();
                label[i][j].setText(startTimeList.get(j).toString());
                calGrid.add(label[i][j], i, j);
            }
        }

        // TODO: Fill this with events

        updateCalendar();
    }



    public void updateCalendar() {
        // TODO: Fill this with events
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": Updating "+JavaFXApp.getCurrentUser().getUsername()+"'s "
                +JavaFXApp.getCurrentSchedule().getID()+" calendar");
        calendarEventList.clear();
        int row;
        ArrayList<Integer> classes = new ArrayList<>();

        if(JavaFXApp.getCurrentSchedule().scheduleSize()>0){
            Event e  = JavaFXApp.getCurrentSchedule().getEvents().get(JavaFXApp.getCurrentSchedule().scheduleSize()-1);
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
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": "+JavaFXApp.getCurrentUser().getUsername()+" hit the back button");
        saveSchedule();
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": "+JavaFXApp.getCurrentUser().getUsername()+" saved their "+
                JavaFXApp.getCurrentSchedule().getID()+" schedule");
        JavaFXApp.changeScene("savedScene.fxml");
    }

    private void saveSchedule() {
        String currUsername = JavaFXApp.getCurrentUser().getUsername();
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": Saving "+currUsername+"'s "
                +JavaFXApp.getCurrentSchedule().getID()+" schedule");
        File savedSchedulesFile;
        FileWriter fw;
        try {
            savedSchedulesFile = new File(savedSchedulePath + "/" + currUsername + ".txt");
            fw = new FileWriter(savedSchedulesFile);
            for (CalendarEvent calendarEvent : calendarEventList) {
                Event event = calendarEvent.getEvent();
                fw.write(event.toString());
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace(); //TODO: Print error message better
        }


    }
}

class SearchResult extends HBox {
    Course course;
    Label courseLabel;
    Button addButton;
    searchFXMLController controller;

    public SearchResult(Course course, searchFXMLController controller) {
        super();
        this.course = course;
        this.controller = controller;
        courseLabel = new Label(course.toString());
        courseLabel.setStyle("-fx-font: 12 monospace;");
        addButton = new Button("+");
        addButton.setOnAction((ActionEvent e) -> {
            JavaFXApp.getCurrentSchedule().addEvent(course);
            controller.updateCalendar();
        });

        this.getChildren().addAll(courseLabel, addButton);
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