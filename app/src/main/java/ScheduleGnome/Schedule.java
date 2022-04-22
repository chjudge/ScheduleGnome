package ScheduleGnome;

import java.util.ArrayList;

public class Schedule {

//    final private String semester;
    final private String name;
    final private boolean isFall;
    ArrayList<Event> events;



    public Schedule(String name, boolean isFall){
//        this.semester = setSemester();
        this.name = name;
        this.isFall = isFall;
        events = new ArrayList<>();
    }

    public void addEvent(Event e){
        if(!events.contains(e))
            events.add(e);
    }

    public void deleteEvent(Event e){
        events.remove(e);
    }

    public Event getEvent(int i){
        return events.get(i);
    }

    public Course getCourse(String courseCode){
        for (Event event : events) {
            if(((Course) event).getCourseCode().equals(courseCode))
                return (Course) event;
        }
        return null;
    }


    public int scheduleSize(){
        return events.size();
    }

    public String getName(){
        return name;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public boolean isFall() { return isFall; }


//  not doing semesters for now
//    public String getSemester() {
//        return semester;
//    }

//    public String setSemester() {
//
//    }

    void downloadSchedule(){

    }



   Calendar getCalendar(){
       Calendar cal = new Calendar(this);
        return cal;
    }

//don't have reference numbers
//   void getRefNums(){
//
//    }


}
