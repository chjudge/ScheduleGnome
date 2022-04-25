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
        int graduationYear = JavaFXApp.getCurrentUser().getGraduationYear();

//            gradYearList.add(null);


        for (int i = 0; i < 4; i++) {
            gradYearList.add(2022 + i);
        }

        gradYear.setItems(gradYearList);
        if(graduationYear != 0)
            gradYear.setValue(graduationYear);
    }

    private void loadMajors() {
        departmentList = FXCollections.observableArrayList();
        departmentList.addAll(JavaFXApp.getDB().getDistinctDepts());
        departmentList.sort(String.CASE_INSENSITIVE_ORDER);

        major.setItems(departmentList);

        major.setValue(JavaFXApp.getCurrentUser().getMajor());
    }

    @FXML
    public void saveProfile(ActionEvent event) throws IOException {
        User user = JavaFXApp.getCurrentUser();

        if(this.gradYear.getValue() != null)
            user.setGraduationYear(this.gradYear.getValue());

        user.setMajor(this.major.getValue());

        JavaFXApp.getDB().updateProfile(user);

        JavaFXApp.changeScene("savedScene.fxml");
    }

    @FXML
    public void cancel(ActionEvent event) throws IOException {
        JavaFXApp.changeScene("savedScene.fxml");
    }
}