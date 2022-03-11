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
        }catch (IOException e){
            System.err.println("IO EXCEPTION");
            e.printStackTrace();
        }
    }

    public void readCourses(ArrayList<Event> events) throws IOException, CsvValidationException {
        String[] lines;
        Course c;
        // bufferedReader.readLine();
        csvReader.skip(1);
        while ((lines = csvReader.readNext()) != null) {
            c = readCourse(lines);
            System.out.println(c);
            events.add(c);
        }
    }

    private Course readCourse(String[] items) {
        
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals("NULL"))
                items[i] = null;
        }

        return new Course(items);

        // DayOfWeek[] meets;

        // if (items[5] == null)
        //     meets = null;
        // else {
        //     String[] days = items[5].split("");
        //     meets = new DayOfWeek[days.length];

        //     for (int i = 0; i < days.length; i++) {
        //         switch (days[i]) {
        //             case "M":
        //                 meets[i] = DayOfWeek.of(1);
        //                 break;
        //             case "T":
        //                 meets[i] = DayOfWeek.of(2);
        //                 break;
        //             case "W":
        //                 meets[i] = DayOfWeek.of(3);
        //                 break;
        //             case "R":
        //                 meets[i] = DayOfWeek.of(4);
        //                 break;
        //             case "F":
        //                 meets[i] = DayOfWeek.of(5);
        //                 break;
        //             default:
        //                 break;
        //         }
        //     }
        // }
        
        // LocalTime start = null, end = null;
        // try {
        //     start = items[3] == null ? null : LocalTime.parse(items[3]);
        // } catch (java.time.format.DateTimeParseException e) {
        //     items[3] = "0" + items[3];
        // }
        // try {
        //     end = items[4] == null ? null : LocalTime.parse(items[3]);
        // } catch (java.time.format.DateTimeParseException e) {
        //     items[4] = "0" + items[4];
        // }

        // System.out.println(Arrays.toString(items));


        // return new Course(items[0], items[1], items[2], start, end, meets,
        //         items[6], items[7],
        //         items[8] == null ? -1 : Integer.parseInt(items[8]),
        //         items[9] == null ? -1 : Integer.parseInt(items[9]));
    }
}