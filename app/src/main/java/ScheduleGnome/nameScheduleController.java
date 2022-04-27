package ScheduleGnome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class nameScheduleController implements Initializable {
    @FXML
    TextField textField;

    ToggleGroup semesterBttns;
    @FXML
    RadioButton fallToggle;
    @FXML
    RadioButton springToggle;

    @FXML private Text nameactiontarget;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": Loading name schedule page");
        semesterBttns = new ToggleGroup();
        fallToggle.setToggleGroup(semesterBttns);
        springToggle.setToggleGroup(semesterBttns);
    }

    public void create(ActionEvent actionEvent) throws IOException {
        if(textField.getText().isBlank()) {
            nameactiontarget.setText("Enter a schedule name");
            return;
        }
        String name = textField.getText();
        boolean duplicateName = false;
        for(String scheduleName : JavaFXApp.getCurrentUser().getSavedSchedules().keySet()) {
            if (name.equals(scheduleName)) {
                duplicateName = true;
                nameactiontarget.setText("Schedule name already exists.");
                break;
            }
        }
        if(!duplicateName) {
            boolean isFall = fallToggle.isSelected();
            Schedule newSched = JavaFXApp.getCurrentUser().addNewSchedule(name, isFall);
            System.out.println("Current user ID:" + JavaFXApp.getCurrentUser().getId());
            JavaFXApp.setCurrentSchedule(newSched);

            JavaFXApp.getDB().addNewSchedule(newSched);
            if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now()) +
                    ": " + JavaFXApp.getCurrentUser().getUsername() + " created " +
                    " a new schedule named " + name);
            JavaFXApp.changeScene("searchScheduleScene.fxml");
            Pane pane = new Pane();
        }
    }

    public void cancel(ActionEvent actionEvent) throws IOException{
        if (JavaFXApp.isLogging) System.out.println(JavaFXApp.dtf.format(LocalDateTime.now())+
                ": "+JavaFXApp.getCurrentUser().getUsername()+" cancelled making" +
                "a new schedule");
        JavaFXApp.changeScene("savedScene.fxml");
    }

}
