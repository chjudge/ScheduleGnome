package ScheduleGnome;



import java.util.ArrayList;
import java.util.Scanner;

public class Schedule {

    final private String semester;
    final private String schedule_id;
    ArrayList<Event> events;


    public Schedule(String schedule_id){
        this.semester = setSemester();
        this.schedule_id = schedule_id;
        events = new ArrayList<>();
    }

    public String getSemester() {
        return semester;
    }

    public String setSemester() {
        System.out.println("Is this schedule for fall or spring semester?");
        Scanner scanner = new Scanner(System.in);
        String semester = scanner.next();
        scanner.close();
        if (semester.equalsIgnoreCase("fall") || semester.equalsIgnoreCase("f")) {
            return "Fall";
        }
        if (semester.equalsIgnoreCase("spring") || semester.equalsIgnoreCase("s")) {
            return "Spring";
        } else {
            System.out.println("invalid semester");
            return null;
        }


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
