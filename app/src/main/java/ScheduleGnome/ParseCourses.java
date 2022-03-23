package ScheduleGnome;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ParseCourses {
    private static Reader reader;
    private static CSVReader csvReader;
    private static final String COURSES = "courses.csv";

    public ParseCourses() {

    
            reader = new InputStreamReader((getClass().getResourceAsStream(COURSES)));
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