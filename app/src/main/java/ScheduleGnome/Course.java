package ScheduleGnome;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Course extends Event {
    private String courseCode;
    private String shortTitle;
    private String building;
    private String room;
    private int enrollment;
    private int capacity;

    public Course(int id, String courseCode, String shortTitle, String longTitle, LocalTime start, LocalTime end,
            DayOfWeek[] dates, String building, String room, int enrollment, int capacity) {
        super(id, longTitle, start, end, dates);
        this.courseCode = courseCode;
        this.shortTitle = shortTitle;
        this.building = building;
        this.room = room;
        this.enrollment = enrollment;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Course [building=" + building + ", capacity=" + capacity + ", courseCode=" + courseCode
                + ", enrollment=" + enrollment + ", room=" + room + ", shortTitle=" + shortTitle + super.toString();
    }
}