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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(int enrollment) {
        this.enrollment = enrollment;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}