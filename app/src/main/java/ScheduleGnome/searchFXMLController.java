package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class searchFXMLController {
    @FXML
    TextField searchField;
    @FXML
    ComboBox<String> departmentChoice;
    @FXML
    ComboBox<Integer> creditChoice;
    @FXML
    ComboBox<LocalTime> startTimeChoice;
    @FXML
    ListView<SearchResult> searchList;
    @FXML
    ListView<CalendarEvent> eventList;
    @FXML
    Button backButton;

    @FXML
    ObservableList<String> departmentList;
    @FXML
    ObservableList<LocalTime> timeList;

    @FXML
    ObservableList<SearchResult> searchResultList;
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
        if(departmentChoice.getValue() != null)
            search.addDept(departmentChoice.getValue());
        if(startTimeChoice.getValue() != null)
            search.addStartTime(startTimeChoice.getValue());

        System.out.println("search query: " + searched);

        ArrayList<Course> results = search.querySearch();

        for (Course course : results) {
            searchResultList.add(new SearchResult(course, this));
        }

        // for (Course course : searchResultList) {
        //     System.out.println(course);
        // }

        System.out.println(search.querySearch().size() + " results");

        searchList.setItems(searchResultList);
    }

    public void initialize() {
        System.out.println("loading search");
        search = JavaFXApp.getSearch();
        searchResultList = FXCollections.observableArrayList();

        departmentList = FXCollections.observableArrayList();
        for (Course course : search.getAllCourses()) {

            if (!departmentList.contains(course.getDept()))
                departmentList.add(course.getDept());
        }

        departmentList.sort(String.CASE_INSENSITIVE_ORDER);

        System.out.println(departmentList.toString());

        departmentChoice.setItems(departmentList);

        timeList = FXCollections.observableArrayList();

        for (int i = 8; i < 16; i++) {
            timeList.add(LocalTime.of(i, 0));
        }

        startTimeChoice.setItems(timeList);

        calendarEventList = FXCollections.observableArrayList();
        // TODO: Fill this with events

        updateCalendar();
        System.out.println("UWU");
        System.out.println("INIT!!!");
    }

    public void updateCalendar() {
        // TODO: Fill this with events
        calendarEventList.clear();
        for (Event e : JavaFXApp.getCurrentSchedule().getEvents()) {
            calendarEventList.add(new CalendarEvent(e, this));
        }
        eventList.setItems(calendarEventList);
    }

    public void back() throws IOException {
        saveSchedule();
        JavaFXApp.changeScene("savedScene.fxml");
    }

    private void saveSchedule() {
        String currUsername = JavaFXApp.getCurrentUser().getUsername();

        File savedSchedulesFile;
        FileWriter fw;
        try {
            savedSchedulesFile = new File(savedSchedulePath + "/"+ currUsername + ".txt");
            fw = new FileWriter(savedSchedulesFile);
            for(CalendarEvent calendarEvent : calendarEventList) {
                Event event = calendarEvent.getEvent();
                fw.write(event.toString());
            }
            fw.close();
        }
        catch (Exception e) {
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