package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.LocalTime;

public class searchFXMLController {
    @FXML TextField searchField;
    @FXML ChoiceBox<String> departmentChoice;
    @FXML ChoiceBox<Integer> creditChoice;
    @FXML ChoiceBox<LocalTime> startTimeChoice;
    @FXML ListView<SearchResult> searchList;
    @FXML ListView<CalendarEvent> eventList;
    @FXML Button backButton;

    @FXML ObservableList<SearchResult> searchResultList;
    @FXML
    static ObservableList<CalendarEvent> calendarEventList; // TODO Need to make some for MTWRF

    Search search;
    
    @FXML protected void search(ActionEvent event){
        if(search == null)
            search = JavaFXApp.getSearch();
        if(searchResultList == null)
            searchResultList = FXCollections.observableArrayList();

        String searched = searchField.getText();
        if(searched.isBlank()) return;
        System.out.println(searched);

        searchResultList.clear();

        //System.out.println("cleared list");

        search.setSearched(searched);

        System.out.println("search query: "+searched);

        for (Course course : search.querySearch()) {
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
        JavaFXApp.changeScene("savedScene.fxml");
    }
}

class SearchResult extends HBox {
    Course course;
    Label courseLabel;
    Button addButton;
    searchFXMLController controller;

    public SearchResult(Course course, searchFXMLController controller){
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

    public CalendarEvent(Event event, searchFXMLController controller){
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
}