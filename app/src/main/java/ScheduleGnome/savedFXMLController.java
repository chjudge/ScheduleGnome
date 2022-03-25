package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class savedFXMLController implements Initializable {
     User user = JavaFXApp.getCurrentUser();
//    ObservableList list = FXCollections.observableArrayList();


//    private ListView<String> savedList;
    @FXML
    private TextField textField;
    @FXML
    private Button button;
    @FXML
    private Label greetingLabel;




//    public void create() throws IOException{
//        user.addNewSchedule(newName.getText());
//        JavaFXApp.changeScene("searchScheduleScene.fxml");
//    }
    @FXML
    public void clickNew(ActionEvent actionEvent) throws IOException{
        JavaFXApp.changeScene("nameSchedule.fxml");
    }



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
//        loadData();
    }

//    private void loadData(){
//        list.removeAll(list);
//        ObservableList<String> items = FXCollections.observableArrayList(list);
//        int i = 1;
//        for (String key : user.getSavedSchedules().keySet()) {
//            list.add(key);
//            i++;
//        }
//       savedList.getItems().addAll(list);
//    }



//    public void selectSchedule(ActionEvent actionEvent){
//
//    }



//    public void deleteSchedule(ActionEvent actionEvent){
//        JavaFXApp.getCurrentUser().deleteSchedule();
//    }




    public void logout(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("loginScene.fxml");


    }

    public void newSchedule(ActionEvent actionEvent)throws IOException{
        JavaFXApp.setCurrentSchedule(new Schedule("" + java.lang.Math.random()));
        Pane pane = new Pane();


    }



}
