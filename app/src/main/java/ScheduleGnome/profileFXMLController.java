package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class profileFXMLController {

    @FXML private ComboBox<Integer> gradYear;
    @FXML private ComboBox<String> major;

    @FXML
    ObservableList<Integer> gradYearList;
    @FXML
    ObservableList<String> departmentList;

    @FXML public void initialize(){
        loadMajors();
        loadGradYears();
    }

    private void loadGradYears() {
        gradYearList = FXCollections.observableArrayList();

        //check profile for grad year
        gradYearList.add(null);

        for (int i = 0; i < 4; i++) {
            gradYearList.add(2022 + i);
        }

        gradYear.setItems(gradYearList);
    }

    private void loadMajors() {
        departmentList = FXCollections.observableArrayList();
        departmentList.addAll(JavaFXApp.getDB().getDistinctDepts());
        departmentList.sort(String.CASE_INSENSITIVE_ORDER);

        departmentList.add(0, null);

        //get current profile and check if it has a major

        major.setItems(departmentList);
    }

    @FXML
    public void saveProfile(ActionEvent event) throws IOException {
        //check if major is null

        //check if graduation year is null



        //use database to save profile

        JavaFXApp.changeScene("savedScene.fxml");
    }

    @FXML
    public void cancel(ActionEvent event) throws IOException {
        JavaFXApp.changeScene("savedScene.fxml");
    }
}