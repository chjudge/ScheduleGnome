package ScheduleGnome;



import java.util.ArrayList;

public class Schedule {

   final private String semester;
    final private String schedule_id;
    ArrayList<Event> events;


    public Schedule(String semester, String schedule_id, Calendar calendar){
        this.semester = semester;
        this.schedule_id = schedule_id;
        events = new ArrayList<>();
    }

    void addEvent(Event e){

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
