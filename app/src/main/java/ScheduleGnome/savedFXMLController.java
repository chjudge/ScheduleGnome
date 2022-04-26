package ScheduleGnome;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class savedFXMLController implements Initializable {


    @FXML
    private ListView<String> listView;
    @FXML
    private ArrayList<String> savedNames;
    @FXML
    private GridView<ScheduleBox> grid;
    @FXML
    private ObservableList<Schedule> obsvSched;



    @FXML
    public void clickNew(ActionEvent actionEvent) throws IOException{
        JavaFXApp.Log(JavaFXApp.getCurrentUser().getUsername()+" selected to make" +
                "a new schedule");
        JavaFXApp.changeScene("nameSchedule.fxml");
    }

    @FXML
    public void clickProfile(ActionEvent actionEvent) throws IOException{
        JavaFXApp.changeScene("profileScene.fxml");
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        //Load in schedules
        JavaFXApp.Log("Loading saved schedules page");
//        obsvSched = FXCollections.observableArrayList();
//        grid = new GridView<>();
//        grid.setCellFactory(new Callback<GridView<Schedule>, GridCell<Schedule>>() {
//            @Override
//            public GridCell<Schedule> call(GridView<Schedule> param) {
//                return new ScheduleCell<>();
//            }
//        });



//        obsvSched.addAll(JavaFXApp.getCurrentUser().savedSchedules.values());
//
//        ObservableList<ScheduleBox> boxes = FXCollections.observableArrayList();
//
//        for (Schedule s :
//                obsvSched) {
//            boxes.add(new ScheduleBox(s));
//        }
//
//        grid.setItems(boxes);
        

        savedNames = new ArrayList<>();
        savedNames.addAll(JavaFXApp.getCurrentUser().savedSchedules.keySet());
        for(String savedName : savedNames) {
            System.out.println(savedName);
        }

        listView.getItems().addAll(savedNames);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    JavaFXApp.setCurrentSchedule(JavaFXApp.getCurrentUser().savedSchedules.get(newValue));
                    JavaFXApp.changeScene("searchScheduleScene.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void compareSchedules(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("compareSchedules.fxml");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("loginScene.fxml");

    }

    class ScheduleBox extends HBox{

        Schedule sched;
        Label schedLabel;
        Label semesterLabel;
        Button removeButton;

        public ScheduleBox(Schedule sched) {
            super();
            this.sched = sched;
            schedLabel = new Label(sched.getName() + (sched.isFall() ? "\nFall" : "\nSpring"));
            removeButton = new Button("x");
            removeButton.setOnAction((ActionEvent e) -> {
                // TODO: DELETE SCHEDULE
            });

            this.getChildren().addAll(schedLabel, removeButton);
        }
    }

    class ScheduleCell<Sched> extends GridCell<Sched> {
        @Override
        protected void updateItem(Sched item, boolean empty){
            super.updateItem(item, empty);
            if(empty){
                setGraphic(null);
            }
            else {
                setGraphic(new ScheduleBox((ScheduleGnome.Schedule) item));
            }
        }


    }
}
