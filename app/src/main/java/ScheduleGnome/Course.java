package ScheduleGnome;

import java.time.LocalTime;

public class Course extends Event {
    private String year;
    private String semester;
    private String department;
    private String courseNumber;
    private String section;
    private int creditHours;
    private int capacity;
    private int enrollment;
    private String professorLastName;
    private String professorFirstName;
    private String professorPreferredName;
    private String comments;


    public Course(String longTitle, LocalTime start, LocalTime end,
            String dates, int enrollment, int capacity) {
        super(longTitle, start, end, dates);
        this.enrollment = enrollment;
        this.capacity = capacity;
    }

    public Course(String[] items) {
        super(items[5], items[9], items[10], items[11], items[12], items[13], items[14], items[15]);

        this.year = items[0];
        this.semester = items[1];
        this.department = items[2];
        this.courseNumber = items[3];
        this.section = items[4];
        this.creditHours = items[6] == null ? -1 : Integer.parseInt(items[6]);
        this.capacity = items[7] == null ? -1 : Integer.parseInt(items[7]);
        this.enrollment = items[8] == null ? -1 : Integer.parseInt(items[8]);
        this.professorLastName = items[16];
        this.professorFirstName = items[17];
        this.professorPreferredName = items[18];
        this.comments = items[19];
    }


    @Override
    public String toString() {

        return String.format("%-40s%-25s\n%-40s%-25s%-15s", getTitle(), getStartTime() + " - " + getEndTime(), getCourseCode(),
                getDatesString(),  enrollment + "/" + capacity);
    }

    public String getCourseCode() {
        return department + " " + courseNumber + " " + section;
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

    public String getDept() {
        return department;
    }

    public String getCode() {
        return courseNumber;
    }

}