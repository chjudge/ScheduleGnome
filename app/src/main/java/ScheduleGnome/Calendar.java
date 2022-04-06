package ScheduleGnome;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class Calendar {
    private Schedule schedule;

    public Calendar(Schedule schedule){
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        String ret = "";
        ret += " schedule\n------------------------------\n\n";
        
        // Event[] eventsPerDay = new Event[5];
        //I would love a more space-efficient solution to this
        // TODO: Do something like this: (not exactly but you get it)
        // TODO: ___Monday___|___Tuesday___|___Wednesday....
        // TODO: Class Name  | Class Name  | Class Name
        // TODO: Class Name  | .....
        ret += dailyScheduleToString();
        ret += "\n";
        return ret;
    }

    private String dailyScheduleToString() {
        String ret = "";

        ArrayList<Event> monEvents = new ArrayList<Event>();
        ArrayList<Event> tueEvents = new ArrayList<Event>();
        ArrayList<Event> wedEvents = new ArrayList<Event>();
        ArrayList<Event> thuEvents = new ArrayList<Event>();
        ArrayList<Event> friEvents = new ArrayList<Event>();
        
//        for(Event event : schedule.events) {
//            for(DayOfWeek day : event.getDates()) {
//                switch(day.getValue()) {
//                    case 1:
//                        monEvents.add(event);
//                    break;
//
//                    case 2:
//                        tueEvents.add(event);
//                    break;
//
//                    case 3:
//                        wedEvents.add(event);
//                    break;
//
//                    case 4:
//                        thuEvents.add(event);
//                    break;
//
//                    case 5:
//                        friEvents.add(event);
//                    break;
//                }
//            }
//        }
        
        String[] weekdays = {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY"};
        for(int i=0; i<5; i++) {
            ret += weekdays[i] + "\n------------------------\n";
            switch(i) {
                case 0:
                    //Make a method for this and add each event to the string
                    ret += eventArrayToString(monEvents);
                break;
                case 1:
                    ret += eventArrayToString(tueEvents);
                break;
                case 2:
                    ret += eventArrayToString(wedEvents);
                break;
                case 3:
                    ret += eventArrayToString(thuEvents);
                break;
                case 4:
                    ret += eventArrayToString(friEvents);
                break;
            }

        }
        return ret;
    }

    private String eventArrayToString(ArrayList<Event> eventList) {
        String ret = "";
        for(Event event : eventList) {
            ret += event.getTitle() + "\n";
        }
        ret += "\n";
        return ret;
    }
}