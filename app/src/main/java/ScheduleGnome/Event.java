package ScheduleGnome;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class Event {
    private final String title;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private String dates;

    public Event(String title, LocalTime start, LocalTime end, String dates) {
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.dates = dates;
    }

    public Event(String title, String m, String t, String w, String r, String f, String b, String e) {
        this.title = title;
        LocalTime start = LocalTime.of(0, 0), end = LocalTime.of(0, 0);

        dates = "";
        for (String day : new String[]{m, t, w, r, f}) {
            dates = dates.concat(day == null ? "" : day);
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.US);

        try {
            start = b == null ? null : LocalTime.parse(b, df);
        } catch (java.time.format.DateTimeParseException ex) {
            if (!b.isBlank()) {
                b = "0" + b;
                start = LocalTime.parse(b, df);
            }
        }
        try {
            end = e == null ? null : LocalTime.parse(e, df);
        } catch (java.time.format.DateTimeParseException ex) {
            if (!e.isBlank()) {
                e = "0" + e;
                end = LocalTime.parse(e, df);
            }
        }

        this.startTime = start;
        this.endTime = end;
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

    public int hasConflictWith(Event e) {
        LocalTime eStart = e.getStartTime();
        LocalTime eEnd = e.getEndTime();

        for (String day : e.getDatesString().split("")) {
            if (dates.contains(day)) {
                if (eStart.compareTo(startTime) >= 0 && eStart.compareTo(endTime) <= 0
                        ||
                        eEnd.compareTo(startTime) >= 0 && eEnd.compareTo(endTime) <= 0) {
                    return 1;
                }
            }
        }
        return 0;
    }

}