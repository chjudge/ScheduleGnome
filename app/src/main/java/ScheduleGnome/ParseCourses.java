package ScheduleGnome;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseCourses {
    private static Reader reader;
    private static CSVReader csvReader;
    private static final String COURSES_PATH = "data/courses.csv";

    public ParseCourses() {

        try {
            reader = Files.newBufferedReader(Paths.get(COURSES_PATH));
            csvReader = new CSVReader(reader);
        } catch (FileNotFoundException e) {
            System.err.println("FILE NOT FOUND");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO EXCEPTION");
            e.printStackTrace();
        }
    }

    public void readCourses(ArrayList<Event> events) {
        String[] lines;
        Course c;
        
        try {
            csvReader.skip(1);
            while ((lines = csvReader.readNext()) != null) {
                c = readCourse(lines);
                events.add(c);
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