package ScheduleGnome;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.PopupWindow;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class searchFXMLController {
    @FXML
    TextField searchField;
    @FXML
    ComboBox<String> departmentChoice;
    @FXML
    ComboBox<LocalTime> startTimeChoice;
    @FXML
    ComboBox<LocalTime> endTimeChoice;
    @FXML
    ListView<SearchResult> searchList;
    @FXML
    ListView<CalendarEvent> eventList;
    @FXML
    Button backButton;

    @FXML
    ObservableList<String> departmentList;
    @FXML
    ObservableList<LocalTime> startTimeList;
    @FXML
    ObservableList<LocalTime> endTimeList;
    @FXML
    ObservableList<SearchResult> searchResultList;
    @FXML
    GridPane calGrid;
    Label[][] label = new Label[6][14];


    @FXML
    static ObservableList<CalendarEvent> calendarEventList;

    Search search;

    private String savedSchedulePath = "./src/main/savedSchedules";

    @FXML
    protected void search() {
        String searched = searchField.getText();
        //if (searched.isBlank()) return;\


        searchResultList.clear();

        //System.out.println("cleared list");

        search.setSearched(searched);
        if (departmentChoice.getValue() != null)
            search.setDept(departmentChoice.getValue());
        if (startTimeChoice.getValue() != null)
            search.setStartTime(startTimeChoice.getValue().toString());
        if (endTimeChoice.getValue() != null)
            search.setEndTime(endTimeChoice.getValue().toString());

        ArrayList<Course> results = search.querySearch();

        for (Course course : results) {
            searchResultList.add(new SearchResult(course, this));
        }
        searchList.setItems(searchResultList);
    }

    public void initialize() {
        JavaFXApp.Log("Loading search!");
        search = new Search(JavaFXApp.getCurrentSchedule().isFall());
        searchResultList = FXCollections.observableArrayList();

        // Dynamic search
        searchField.textProperty().addListener(
                (obj, oldVal, newVal) -> {
                    if (newVal.length() < oldVal.length() ||
                            newVal.endsWith(" ")) return;
                    search();
                }
        );

        departmentList = FXCollections.observableArrayList();
        departmentList.addAll(JavaFXApp.getDB().getDistinctDepts());
        departmentList.sort(String.CASE_INSENSITIVE_ORDER);

        departmentList.add(0, null);

        System.out.println(departmentList.toString());

        departmentChoice.setItems(departmentList);

        startTimeList = FXCollections.observableArrayList();
        endTimeList = FXCollections.observableArrayList();

        startTimeList.add(null);
        endTimeList.add(null);

        for (int i = 8; i < 22; i++) {
            startTimeList.add(LocalTime.of(i, 0));
            endTimeList.add(LocalTime.of(i, 0));
        }

        startTimeChoice.setItems(startTimeList);
        endTimeChoice.setItems(endTimeList);

        calendarEventList = FXCollections.observableArrayList();

        for (int i = 0; i < 1; i++) {
            for (int j = 1; j < label[i].length; j++) {
                label[i][j] = new Label();
                label[i][j].setText(startTimeList.get(j).toString());
                calGrid.add(label[i][j], i, j);
            }
        }
        updateCalendar();
    }


    public void updateCalendar() {
        JavaFXApp.Log("Updating " + JavaFXApp.getCurrentUser().getUsername() + "'s " +
                JavaFXApp.getCurrentSchedule().getName() + " calendar");
        calendarEventList.clear();
        int row;
        ArrayList<Integer> classes = new ArrayList<>();
        calGrid.getChildren().clear();
        calGrid.setGridLinesVisible(false);
        for (int i = 0; i < 1; i++) {
            for (int j = 1; j < label[i].length; j++) {
                label[i][j] = new Label();
                if (startTimeList.get(j).getHour() > 12) {
                    label[i][j].setText(startTimeList.get(j).minusHours(12).toString());
                } else {
                    label[i][j].setText(startTimeList.get(j).toString());
                }
                calGrid.add(label[i][j], i, j);

            }
        }
        calGrid.add(new Label("Monday"), 1, 0);
        calGrid.add(new Label("Tuesday"), 2, 0);
        calGrid.add(new Label("Wednesday"), 3, 0);
        calGrid.add(new Label("Thursday"), 4, 0);
        calGrid.add(new Label("Friday"), 5, 0);

        for (int i = 0; i < JavaFXApp.getCurrentSchedule().scheduleSize(); i++) {
            Event e = JavaFXApp.getCurrentSchedule().getEvents().get(i);
            System.out.println(e.getTitle());
            row = e.getStartTime().getHour();

            //determines which columns the course should be in
            if (e.getDatesString().contains("M")) {
                classes.add(1);
            }
            if (e.getDatesString().contains("T")) {
                classes.add(2);
            }
            if (e.getDatesString().contains("W")) {
                classes.add(3);
            }
            if (e.getDatesString().contains("R")) {
                classes.add(4);
            }
            if (e.getDatesString().contains("F")) {
                classes.add(5);
            }
            int tRow = row - 7;
            //creates labels for the added course
            for (Integer aClass : classes) {
                label[aClass][tRow] = new Label();
                label[aClass][tRow].setText(e.getTitle());
                label[aClass][tRow].setWrapText(true);

                JavaFXApp.Log("Adding " + label[aClass][tRow].getText() + " to " + aClass + " " + tRow);

                PopOver popOver = new PopOver(new CalendarEvent(e, this));


                label[aClass][tRow].setOnMouseEntered(me -> {
                    System.out.println("Mouse Entered" + e.getTitle());
                    popOver.show(label[aClass][tRow]);
                });
                label[aClass][tRow].setOnMouseClicked(me -> {
                    System.out.println("Mouse Clicked" + e.getTitle());
                            popOver.hide();
                        });

                System.out.println(label[aClass][tRow].getOnMouseEntered().toString());

                calGrid.add(new CalendarEvent(e, this), aClass, tRow);

//                calGrid.add(label[aClass][tRow], aClass, row - 7);
            }

            for (Node n : calGrid.getChildren()) {
                if (n instanceof Label) {

                }
            }
            classes.clear();
        }
    }

    public void back() throws IOException {
        JavaFXApp.Log(JavaFXApp.getCurrentUser().getUsername() + " hit the back button");
        saveSchedule();
        JavaFXApp.Log(JavaFXApp.getCurrentUser().getUsername() + " saved their " +
                JavaFXApp.getCurrentSchedule().getName() + " schedule");
        JavaFXApp.changeScene("savedScene.fxml");
    }

    private void saveSchedule() {
        String currUsername = JavaFXApp.getCurrentUser().getUsername();
        JavaFXApp.Log("Saving " + currUsername + "'s "
                + JavaFXApp.getCurrentSchedule().getName() + " schedule");
        //Save courses to schedule
        JavaFXApp.getDB().saveSchedule(JavaFXApp.getCurrentSchedule());
    }

    public void addOwn(ActionEvent actionEvent) throws IOException {
        JavaFXApp.changeScene("addOwnEvent.fxml");
    }

    public void test(MouseEvent me) {
        JavaFXApp.Log("Test grid button pressed");
    }
}

class SearchResult extends HBox {
    Course course;
    Event conflict;
    Label courseLabel;
    Label errorLabel;
    searchFXMLController controller;
    Label recommendLabel;

    public SearchResult(Course course, searchFXMLController controller) {
        super();
        this.course = course;
        this.controller = controller;
        this.conflict = JavaFXApp.getCurrentSchedule().hasConflicts(course);
        courseLabel = new Label(course.toString());

        //check if the course should be recommended
        String recString = JavaFXApp.getCurrentUser().isRecommended(course) ? "Suggested" : "";
        recommendLabel = new Label(String.format("%10s", recString));

        errorLabel = new Label();
        errorLabel.setStyle("-fx-font: 12 monospace;");
        recommendLabel.setStyle("-fx-font: 12 monospace;");
        courseLabel.setStyle("-fx-font: 12 monospace;");
        Event conflict = JavaFXApp.getCurrentSchedule().hasConflicts(course);
        if (conflict == null) {
            Button addButton = new Button("+");
            if (course.hasPrerequisite()) {
                Tooltip t = new Tooltip("Requires:\n" + course.getComments());
                t.setShowDelay(Duration.millis(0));
                addButton.setTooltip(t);
            }
            addButton.setOnAction((ActionEvent e) -> {
                JavaFXApp.getCurrentSchedule().addEvent(course);
                controller.updateCalendar();
                controller.search();
            });
            this.getChildren().addAll(courseLabel, recommendLabel, addButton);
        } else {
            try {
                Course crsConflict = (Course) conflict;
                JavaFXApp.Log("CONFLICT: " + crsConflict.getCourseCode() + " CRS: " + course.getCourseCode());
                if (crsConflict.getCourseCode().equals(course.getCourseCode())) {
                    errorLabel.setText("Already in schedule");
                    this.getChildren().addAll(courseLabel, recommendLabel, errorLabel);
                } else {
                    Button swapButton = createConflictButton(crsConflict);
                    String conflictCode = crsConflict.getDept() + crsConflict.getNumber();
                    String crsCode = course.getDept() + course.getNumber();
                    if (conflictCode.equals(crsCode)) {
                        swapButton.setOnAction((ActionEvent e) -> {
                            Schedule currSched = JavaFXApp.getCurrentSchedule();
                            currSched.deleteEvent(crsConflict);
                            Event conflicting = currSched.hasConflicts(course);
                            if (conflicting == null) {
                                JavaFXApp.getCurrentSchedule().addEvent(course);
                                controller.updateCalendar();
                                controller.search();
                            } else {
                                //JavaFXApp.Log("manually resolve conflict: " + conflicting.getTitle());
                                highlightConflict(conflicting.getTitle(), "red");

                                DialogPane dialogPane = new DialogPane();

                                dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                                dialogPane.setContentText("Cannot automatically swap courses, conflicts with other courses.\nWould you like to replace " + conflicting.getTitle() + "\nwith " + course.getTitle() + "?");

                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.setDialogPane(dialogPane);
                                dialog.setTitle("Conflict");

                                dialog.showAndWait().ifPresent(response -> {
                                    if (response == ButtonType.OK) {
                                        currSched.deleteEvent(conflicting);
                                        currSched.addEvent(course);
                                        controller.updateCalendar();
                                        controller.search();
                                    } else {
                                        JavaFXApp.getCurrentSchedule().addEvent(crsConflict);
                                        highlightConflict(conflicting.getTitle(), "black");
                                    }
                                });


//                                errorLabel.setText("   INVALID SWAP");
//                                errorLabel.setStyle("-fx-text-fill: red;");
                            }
                        });
                        errorLabel.setText("  SECTION CONFLICT:\n  " + crsConflict.getCourseCode());
                    } else {
                        swapButton.setOnAction((ActionEvent e) -> {
                            JavaFXApp.getCurrentSchedule().deleteEvent(crsConflict);
                            JavaFXApp.getCurrentSchedule().addEvent(course);
                            controller.updateCalendar();
                            controller.search();
                        });
                        errorLabel.setText("  TIME CONFLICT:\n  " + crsConflict.getCourseCode());
                    }
                    this.getChildren().addAll(courseLabel, recommendLabel, swapButton, errorLabel);
                }
            } catch (ClassCastException exc) { // extracurricular
                errorLabel.setText("  EXTRACURRICULAR:  \n" + conflict.getTitle());
                this.getChildren().addAll(courseLabel, errorLabel);
            }
        }
    }

    private Button createConflictButton(Course conflicted) {
        Button b = new Button("Swap");
        b.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> {
                    b.setEffect(new DropShadow());
                    highlightConflict(conflict.getTitle(), "red");
                });

        b.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> {
                    b.setEffect(null);
                    highlightConflict(conflict.getTitle(), "black");
                });
        return b;
    }

    private void highlightConflict(String title, String cssColor) {
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 14; j++) {
                Label l = controller.label[i][j];
                if (l != null && l.getText().equals(conflict.getTitle()))
                    l.setStyle("-fx-text-fill: " + cssColor + ";");
            }
        }
    }
}

class CalendarEvent extends HBox {
    Event event;
    Label eventLabel;
    Button removeButton;
    searchFXMLController controller;

    public CalendarEvent(Event event, searchFXMLController controller) {
        super();
        this.event = event;
        this.controller = controller;
        eventLabel = new Label(event.getTitle()); // TODO: For now
        removeButton = new Button("x");
        removeButton.setPadding(new Insets(0, 5, 0, 5));
        removeButton.setFocusTraversable(false);
        removeButton.setOnAction((ActionEvent e) -> {
            JavaFXApp.getCurrentSchedule().deleteEvent(event);
            JavaFXApp.getDB().deleteEvent(event, JavaFXApp.getCurrentSchedule());
            controller.updateCalendar();
        });

        Popup popup = new Popup(event);

        PopOver popOver = new PopOver(popup);

        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.setAnchorLocation(PopOver.AnchorLocation.WINDOW_TOP_LEFT);

        this.setOnMouseEntered(me -> {
            popOver.show(this);
        });

        this.setOnMouseExited(me -> {
            popOver.hide();
        });

        this.getChildren().addAll(eventLabel, removeButton);


    }

    public Event getEvent() {
        return event;
    }
}

class Popup extends VBox {
    public Popup(Event event) {
        super();
        Label name = new Label(event.getTitle());

        this.getChildren().add(name);
        if(event instanceof Course) {
            Course course = (Course) event;
            Label courseCode = new Label("Course Code: " + course.getCourseCode());
            Label courseCredits = new Label("Credit Hours: " + course.getCreditHours());
            this.getChildren().addAll(courseCode, courseCredits);
            if(!course.getComments().isBlank()) {
                Label courseDescription = new Label("Course Information: " + course.getComments());
                this.getChildren().add(courseDescription);
            }
        }

        this.setPadding(new Insets(10, 10, 10, 10));
    }
}

class CalendarLine {
    CalendarEvent[] events;

    public CalendarLine(CalendarEvent[] events) {
        this.events = events;
    }
}
