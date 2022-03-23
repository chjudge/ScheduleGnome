package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class searchFXMLController {
    @FXML TextField searchField;
    @FXML ChoiceBox departmentChoice;
    @FXML ChoiceBox creditChoice;
    @FXML ChoiceBox startTimeChoice;
    @FXML ListView<Event> searchList;
    
    @FXML protected void search(ActionEvent event){

    }

    protected void intialize() {
        //fill this with events idk how
        searchList.setItems(FXCollections.observableArrayList());
        //JavaFXApp.getSearch()
    }
}