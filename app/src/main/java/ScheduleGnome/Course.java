package ScheduleGnome;

import java.time.LocalTime;
import java.util.Objects;

public class Course extends Event {
    private String year;
    private String semester;
    private String department;
    private String courseNumber;
    private String section;
    private int creditHours;
    private int capacity;
    private int enrollment;
    private String professor;
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
        int sem = items[1] == null ? -1 : Integer.parseInt(items[1]);
        this.semester = sem == 10 ? "Fall" : sem == 30 ? "Spring" : "";
        this.department = items[2];
        this.courseNumber = items[3];
        this.section = items[4];
        this.creditHours = items[6] == null ? -1 : Integer.parseInt(items[6]);
        this.capacity = items[7] == null ? -1 : Integer.parseInt(items[7]);
        this.enrollment = items[8] == null ? -1 : Integer.parseInt(items[8]);
        this.professor = items[18].isEmpty() ? items[17] : items[18] + items[16];
        this.comments = items[19];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Course course = (Course) o;
        return creditHours == course.creditHours && capacity == course.capacity && enrollment == course.enrollment && Objects.equals(year, course.year) && Objects.equals(semester, course.semester) && Objects.equals(department, course.department) && Objects.equals(courseNumber, course.courseNumber) && Objects.equals(section, course.section) && Objects.equals(professor, course.professor) && Objects.equals(comments, course.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), year, semester, department, courseNumber, section, creditHours, capacity, enrollment, professor, comments);
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

    public String getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public String getProfessor() {
        return professor;
    }

    public String getComments() {
        return comments;
    }
}