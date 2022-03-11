package ScheduleGnome;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public abstract class Event {
    private String title;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek[] dates;

    public Event(String title, LocalTime start, LocalTime end, DayOfWeek[] dates) {
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.dates = dates;
    }

    public Event(String title, String s, String e, String day){
        this.title = title;
        
        LocalTime start = null, end = null;
        try {
            start = s == null ? null : LocalTime.parse(s);
        } catch (java.time.format.DateTimeParseException ex) {
            s = "0" + s;
        }
        try {
            end = e == null ? null : LocalTime.parse(e);
        } catch (java.time.format.DateTimeParseException ex) {
            e = "0" + e;
        }

        this.startTime = start;
        this.endTime = end;

        DayOfWeek[] meets;

        if (day == null)
            meets = null;
        else {
            String[] days = day.split("");
            meets = new DayOfWeek[days.length];

            for (int i = 0; i < days.length; i++) {
                switch (days[i]) {
                    case "M":
                        meets[i] = DayOfWeek.of(1);
                        break;
                    case "T":
                        meets[i] = DayOfWeek.of(2);
                        break;
                    case "W":
                        meets[i] = DayOfWeek.of(3);
                        break;
                    case "R":
                        meets[i] = DayOfWeek.of(4);
                        break;
                    case "F":
                        meets[i] = DayOfWeek.of(5);
                        break;
                    default:
                        break;
                }
            }
        }

        dates = meets.clone();
    }

    @Override
    public String toString() {
        return title + ", from " + startTime + " to " + endTime +
        " on " + Arrays.toString(dates) ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public DayOfWeek[] getDates() {
        return dates;
    }

    public void setDates(DayOfWeek[] dates) {
        this.dates = dates;
    }

}