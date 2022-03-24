package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.util.ArrayList;

public class searchFXMLController{
    @FXML TextField searchField;
    @FXML ChoiceBox<String> departmentChoice;
    @FXML ChoiceBox<Integer> creditChoice;
    @FXML ChoiceBox<LocalTime> startTimeChoice;
    @FXML ListView<SearchResult> searchList;

    @FXML ObservableList<SearchResult> searchResultList;

    Search search;
    
    @FXML protected void search(ActionEvent event){
        System.out.println("tringtg");

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

        ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();

        for (Course course : search.querySearch()) {
            searchResults.add(new SearchResult(course));
        }

        searchResultList.addAll(searchResults);
        
        // for (Course course : searchResultList) {
        //     System.out.println(course);
        // }

        System.out.println(search.querySearch().size() + " results");

        searchList.setItems(searchResultList);
    }

    @FXML public void intialize() {
        System.out.println("loading search");


        //fill this with events idk how
    }
}

class SearchResult extends HBox {
    Course course;
    Label courseLabel;
    Button addButton;

    public SearchResult(Course course){
        super();
        this.course = course;
        courseLabel = new Label(course.toString());
        addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            JavaFXApp.getCurrentSchedule().addEvent(course);
        });

        this.getChildren().addAll(courseLabel, addButton);
    }
}