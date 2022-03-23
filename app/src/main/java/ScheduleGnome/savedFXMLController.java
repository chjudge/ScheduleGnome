package ScheduleGnome;

import javafx.event.ActionEvent;

import java.io.IOException;

public class savedFXMLController {



    public void logout(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("loginScene.fxml");

    }

    public void newSchedule(ActionEvent actionEvent)throws IOException{
        JavaFXApp.changeScene("searchScheduleScene.fxml");
    }



}
