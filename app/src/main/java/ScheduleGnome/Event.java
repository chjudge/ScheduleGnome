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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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