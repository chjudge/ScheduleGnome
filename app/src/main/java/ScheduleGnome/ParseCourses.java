package ScheduleGnome;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.Reader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class ParseCourses {
    private static CSVReader csvReader;
    private static final String COURSES = "new_courses.csv";

    public ParseCourses() {
        Reader reader = new InputStreamReader((Objects.requireNonNull(getClass().getResourceAsStream(COURSES))));
            csvReader = new CSVReader(reader);
        
    }

    public void readCourses(ArrayList<Course> courses) {
        String[] lines;
        Course c;
        
        try {
            csvReader.skip(1);
            while ((lines = csvReader.readNext()) != null) {
                c = readCourse(lines);
                courses.add(c);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private Course readCourse(String[] items) {

        for (int i = 0; i < items.length; i++) {
            if (items[i].equals("NULL"))
                items[i] = null;
        }

        return new Course(items);
    }
}