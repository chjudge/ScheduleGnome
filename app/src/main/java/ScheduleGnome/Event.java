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
            start = s == null ? null : LocalTime.parse(s);
        }
        try {
            end = e == null ? null : LocalTime.parse(e);
        } catch (java.time.format.DateTimeParseException ex) {
            e = "0" + e;
            end = e == null ? null : LocalTime.parse(e);
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(dates);
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (!Arrays.equals(dates, other.dates))
            return false;
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        //Print dates in a concise and understandable fashion
        char[] datesChars = {'M','T','W','R','F','S','S'};
        String datesString = "";
        for(DayOfWeek date : dates) {
            datesString += datesChars[date.getValue() - 1];
        }

        return title + ", from " + startTime + " to " + endTime +
        " on " + datesString;
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