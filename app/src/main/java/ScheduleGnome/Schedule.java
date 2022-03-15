package ScheduleGnome;



import java.util.ArrayList;

public class Schedule {

    final private String semester;
    final private String schedule_id;
    ArrayList<Event> events;


    public Schedule(String semester, String schedule_id){
        this.semester = semester;
        this.schedule_id = schedule_id;
        events = new ArrayList<>();
    }

    public String getSemester() {
        return semester;
    }

    void addEvent(Event e){
        events.add(e);
    }

    void updateCalendar(){

    }

   void viewCalendar(){

    }

    void displaySchedule(){

    }

   void deleteEvent(Event e){
        events.remove(e);
    }

   void getRefNums(){

    }

   void downloadSchedule(){

    }
}
