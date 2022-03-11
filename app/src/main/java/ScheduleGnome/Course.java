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

    public Course(String courseCode, String shortTitle, String longTitle, LocalTime start, LocalTime end,
            DayOfWeek[] dates, String building, String room, int enrollment, int capacity) {
        super(longTitle, start, end, dates);
        this.courseCode = courseCode;
        this.shortTitle = shortTitle;
        this.building = building;
        this.room = room;
        this.enrollment = enrollment;
        this.capacity = capacity;
    }

    public Course(String[] items){
        super(items[2], items[3], items[4], items[5]);

        this.courseCode = items[0];
        this.shortTitle = items[1];
        this.building = items[6];
        this.room = items[7];
        this.enrollment = items[8] == null ? -1 : Integer.parseInt(items[8]);
        this.capacity = items[9] == null ? -1 : Integer.parseInt(items[9]);
    }

    @Override
    public String toString() {
        return super.toString() + ", in " + building + " room " + room + ", with " +
        enrollment + "/" + capacity + ", shortTitle= " + shortTitle + " code= "+courseCode;
    }
}