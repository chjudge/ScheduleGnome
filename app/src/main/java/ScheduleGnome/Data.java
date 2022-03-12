package ScheduleGnome;

import java.util.ArrayList;

public class Data {
    ArrayList<Course> courses;

    public Data() {
        courses = new ArrayList<>();
        ParseCourses parser = new ParseCourses();
        parser.readCourses(courses);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    
}
