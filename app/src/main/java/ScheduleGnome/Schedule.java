package ScheduleGnome;

import java.util.ArrayList;

public class Schedule {

//    final private String semester;
    final private String schedule_id;
    ArrayList<Event> events;



    public Schedule(String schedule_id){
//        this.semester = setSemester();
        this.schedule_id = schedule_id;
        events = new ArrayList<>();
    }

    public void addEvent(Event e){
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

    public String getID(){
        return schedule_id;
    }


//  not doing semesters for now
//    public String getSemester() {
//        return semester;
//    }

//    public String setSemester() {
//
//    }

    void downloadSchedule(){

    }



    void updateCalendar(){

    }

   Calendar getCalendar(){
       Calendar c = new Calendar(this);
        return c;
    }

//don't have reference numbers
//   void getRefNums(){
//
//    }


}
