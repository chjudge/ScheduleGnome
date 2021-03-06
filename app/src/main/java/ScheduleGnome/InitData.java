package ScheduleGnome;

import java.time.LocalTime;
import java.util.ArrayList;

public class InitData {
    public static void main(String[] args) {
        ArrayList<Course> courses = new ArrayList<>();
        ParseCourses parser = new ParseCourses();
        parser.readCourses(courses);



        DBOperator db = new DBOperator();

        for (Course crs : courses) {
            if(crs.getEndTime().isAfter(crs.getStartTime()) && !crs.getDatesString().isBlank())
                db.insertCourse(crs);
        }
        System.out.println("All done!");
    }
}
