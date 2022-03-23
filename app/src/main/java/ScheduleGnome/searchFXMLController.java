package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.stream.Collectors;

public class searchFXMLController{
    @FXML TextField searchField;
    @FXML ChoiceBox departmentChoice;
    @FXML ChoiceBox creditChoice;
    @FXML ChoiceBox startTimeChoice;
    @FXML ListView<Course> searchList;

    @FXML ObservableList<Course> searchResultList;

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

        //System.out.println("cleared list");

        search.setSearched(searched);

        System.out.println("search query: "+searched);

        searchResultList.addAll(search.querySearch());
        
        for (Course course : searchResultList) {
            System.out.println(course);
        }

        searchList.setItems(searchResultList);
    }

    @FXML public void intialize() {
        System.out.println("loading search");


        //fill this with events idk how
    }
}