package ScheduleGnome;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class Event {
    private String title;
    private LocalTime startTime;
    private LocalTime endTime;
    private String dates;

    public Event(String title, LocalTime start, LocalTime end, String dates) {
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.dates = dates;
    }

    public Event(String title, String m, String t, String w, String r, String f, String b, String e) {
        this.title = title;
        LocalTime start = null, end = null;

        dates = "";
        for (String day : new String[]{m, t, w, r, f}) {
            dates =dates.concat(day == null ? "" : day);
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.US);

        try {
            start = b == null ? null : LocalTime.parse(b, df);
        } catch (java.time.format.DateTimeParseException ex) {
            if(!b.isBlank()) {
                b = "0" + b;
                start = LocalTime.parse(b, df);
            }
        }
        try {
            end = e == null ? null : LocalTime.parse(e, df);
        } catch (java.time.format.DateTimeParseException ex) {
            if(!e.isBlank()) {
                e = "0" + e;
                end = LocalTime.parse(e, df);
            }
        }

        this.startTime = start;
        this.endTime = end;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + dates.hashCode();
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
        if (!dates.equals(other.dates))
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
        return title + ", from " + startTime + " to " + endTime +
        " on " + getDatesString();
    }

    public String getDatesString() {

        return dates;
    }

    public String getTitle() {
        return title;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

}