package ScheduleGnome;

import com.sun.tools.javac.Main;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class savedFXMLController {
    
    public void userLogOut(ActionEvent event) throws IOException {
        JavaFXApp javaFXApp = new JavaFXApp();
        javaFXApp.changeScene("loginScene.fxml");
    }
}
