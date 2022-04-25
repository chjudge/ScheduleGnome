package ScheduleGnome;

import java.util.ArrayList;

public class Schedule {

//    final private String semester;
    final private String name;
    final private boolean isFall;
    final private User user;
    private int id;
    ArrayList<Event> events;



    public Schedule(String name, boolean isFall, User user){
//        this.semester = setSemester();
        this.user = user;   //TODO: change constructor to do this right
        this.name = name;
        this.isFall = isFall;
        events = new ArrayList<>();
    }

    public void addEvent(Event e){
        if(!events.contains(e) && !hasConflicts(e)) {
            System.out.println("adding " + e.getTitle());
            events.add(e);
        }
    }
    // LOL!
    private boolean hasConflicts(Event e) {
        try {
            Course c = (Course)e;
            for (Event event : events) {
                if (c.hasConflictWith(event)) return true;
            }
        } catch (ClassCastException exc) {
            for (Event event : events) {
                if (e.hasConflictWith(event)) return true;
            }
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
