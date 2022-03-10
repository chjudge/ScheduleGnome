package ScheduleGnome;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public abstract class Event {
    private int id;
    private String title;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek[] dates;

    public Event(int id, String title, LocalTime start, LocalTime end, DayOfWeek[] dates) {
        this.id = id;
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", endTime=" + endTime + ", startTime=" + startTime +
        ", dates=" + Arrays.toString(dates) + ", title=" + title + "]";
    }

}