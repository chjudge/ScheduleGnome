package ScheduleGnome;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Course extends Event {
    private String year;
    private String semester;
    private String department;
    private int number;
    private String section;
    private int creditHours;
    private int capacity;
    private int enrollment;
    private String professor;
    private String comments;
    private ArrayList<Course> prereqs;
    private int id;


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
        this.number = items[3] == null ? -1 : Integer.parseInt(items[3]);
        this.section = items[4];
        this.creditHours = items[6] == null ? -1 : Integer.parseInt(items[6]);
        this.capacity = items[7] == null ? -1 : Integer.parseInt(items[7]);
        this.enrollment = items[8] == null ? -1 : Integer.parseInt(items[8]);
        this.professor = (items[18].isEmpty() ? items[17] : items[18]) + " " + items[16];
        this.comments = items[19];
        initializePrereqs();
    }

    public Course(ResultSet result) throws SQLException {
        super(result.getString(4), result.getObject(12, LocalTime.class),
                result.getObject(13, LocalTime.class), result.getString(11));
        id = result.getInt(1);
        year = result.getString(2);
        semester = result.getString(3);
        department = result.getString(5);
        number = result.getInt(6);
        section = result.getString(7);
        creditHours = result.getInt(8);
        capacity = result.getInt(9);
        enrollment = result.getInt(10);
        professor = result.getString(14);
        comments = result.getString(15);
        initializePrereqs();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Course course = (Course) o;
        return creditHours == course.creditHours && capacity == course.capacity && enrollment == course.enrollment && Objects.equals(year, course.year) && Objects.equals(semester, course.semester) && Objects.equals(department, course.department) && Objects.equals(number, course.number) && Objects.equals(section, course.section) && Objects.equals(professor, course.professor) && Objects.equals(comments, course.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), year, semester, department, number, section, creditHours, capacity, enrollment, professor, comments);
    }

    @Override
    public String toString() {

        return String.format("%-40s%-25s\n%-40s%-25s%-5s", getTitle(), getStartTime() + " - " + getEndTime(), getCourseCode(),
                getDatesString(), enrollment + "/" + capacity);
    }

    public String getCourseCode() {
        return department + " " + number + " " + section;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDept() {
        return department;
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

    public int getNumber() {
        return number;
    }

    public String getSection() {
        return section;
    }

    public int getId() {
        return id;
    }

    private void initializePrereqs() {
        prereqs = new ArrayList<>();
        //parse course codes from comments string
        String[] prereqsStrings = comments.split("\\s+");
        for (String prereqString : prereqsStrings) {

        }
    }

    @Override
    public int hasConflictWith(Event e) {
        if (e instanceof Course) {

            Course c = (Course) e;
            if (c.getNumber() == number && c.getDept().equals(department))
                return 2;
        }
        return super.hasConflictWith(e);
    }
}