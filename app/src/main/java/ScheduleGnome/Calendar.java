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
        ret += schedule.getSemester() + " schedule\n------------------------------\n\n";
        
        // Event[] eventsPerDay = new Event[5];
        //I would love a more space-efficient solution to this
        ArrayList<Event> monEvents = new ArrayList<Event>();
        ArrayList<Event> tueEvents = new ArrayList<Event>();
        ArrayList<Event> wedEvents = new ArrayList<Event>();
        ArrayList<Event> thuEvents = new ArrayList<Event>();
        ArrayList<Event> friEvents = new ArrayList<Event>();
        
        for(Event event : schedule.events) {
            for(DayOfWeek day : event.getDates()) {
                switch(day.getValue()) {
                    case 1:
                        monEvents.add(event);
                    break;

                    case 2:
                        tueEvents.add(event);
                    break;

                    case 3:
                        wedEvents.add(event);
                    break;

                    case 4:
                        thuEvents.add(event);
                    break;

                    case 5:
                        friEvents.add(event);
                    break;
                }
            }
            ret += event + "\n";
        }
        
        String[] weekdays = {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY"};
        for(int i=0; i<5; i++) {
            ret += weekdays[i] + "\n------------------------\n";
            switch(i) {
                case 0:
                    ret += monEvents.toString();
                    ret += "\n";
                break;
                case 1:
                    ret += tueEvents.toString();
                    ret += "\n";
                break;
                case 2:
                    ret += wedEvents.toString();;
                    ret += "\n";
                break;
                case 3:
                    ret += thuEvents.toString();
                    ret += "\n";
                break;
                case 4:
                    ret += friEvents.toString();
                    ret += "\n";
                break;
            }

        }
        
        ret += "\n";
        return ret;
    }
}