package ScheduleGnome;

import java.io.IOException;
import java.util.ArrayList;

public class ParseTest {
    public static void main(String[] args) {

    //   LocalTime time1 = LocalTime.parse("10:15:30");
    //   System.out.println(time1);




        ArrayList<Event> events = new ArrayList<Event>();
        ParseCourses parser = new ParseCourses();
        try {
            parser.readCourses(events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}