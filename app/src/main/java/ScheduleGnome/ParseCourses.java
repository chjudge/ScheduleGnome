package ScheduleGnome;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ParseCourses {
    private static File courses;
    private static FileReader fileReader;
    private static BufferedReader bufferedReader;
    private static final String COURSES_PATH = "data/courses.csv";

    public ParseCourses() {
        courses = new File(COURSES_PATH);

        try {
            fileReader = new FileReader(courses);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public void readCourses(ArrayList<Event> events) throws IOException {
        String line;
        Course c;
        bufferedReader.readLine();
        for (; (line = bufferedReader.readLine()) != null;) {
            c = readCourse(line, events.size());
            System.out.println(c);
            events.add(c);
        }
    }

    private Course readCourse(String line, int id) {
        String[]items;

        if(!line.contains("\""))
            items = line.split(",");
        else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                if(line.charAt(i) == '"');

            }
            items = new String[10];
        }
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals("NULL"))
                items[i] = null;
        }

        DayOfWeek[] meets;

        if (items[5] == null)
            meets = null;
        else {
            String[] days = items[5].split("");
            meets = new DayOfWeek[days.length];

            for (int i = 0; i < days.length; i++) {
                switch (days[i]) {
                    case "M":
                        meets[i] = DayOfWeek.of(1);
                        break;
                    case "T":
                        meets[i] = DayOfWeek.of(2);
                        break;
                    case "W":
                        meets[i] = DayOfWeek.of(3);
                        break;
                    case "R":
                        meets[i] = DayOfWeek.of(4);
                        break;
                    case "F":
                        meets[i] = DayOfWeek.of(5);
                        break;
                    default:
                        break;
                }
            }
        }
        
        LocalTime start = null, end = null;
        try {
            start = items[3] == null ? null : LocalTime.parse(items[3]);
        } catch (java.time.format.DateTimeParseException e) {
            items[3] = "0" + items[3];
        }
        try {
            end = items[4] == null ? null : LocalTime.parse(items[3]);
        } catch (java.time.format.DateTimeParseException e) {
            items[4] = "0" + items[4];
        }

        System.out.println(Arrays.toString(items));

        return new Course(id, items[0], items[1], items[2], start, end, meets,
                items[6], items[7],
                items[8] == null ? -1 : Integer.parseInt(items[8]),
                items[9] == null ? -1 : Integer.parseInt(items[9]));
    }
}