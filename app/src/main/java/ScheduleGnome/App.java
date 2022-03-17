/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ScheduleGnome;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class App {

    public static void initSampleData(Schedule schedule) {
        LocalTime start1 = LocalTime.of(10,0,0);
        LocalTime end1 = LocalTime.of(10,50,0);
        DayOfWeek[] days1 = {DayOfWeek.of(1), DayOfWeek.of(3), DayOfWeek.of(5)};
        Course class1 = new Course("ELEE 304 A","Emag","ELECTROMAGNETIC THEORY", start1, end1, days1,"HOYT", "113", 30, 36);
        schedule.addEvent(class1);

        LocalTime start2 = LocalTime.of(1,0,0);
        LocalTime end2 = LocalTime.of(1,50,0);
        DayOfWeek[] days2 = {DayOfWeek.of(1), DayOfWeek.of(3), DayOfWeek.of(5)};
        Course class2 = new Course("ENGR 274 A","Emath", "MATHEMATICAL METHODS IN ENGINEERING", start2, end2, days2,"HOYT", "218", 15, 20);
        schedule.addEvent(class2);

        LocalTime start3 = LocalTime.of(2,0,0);
        LocalTime end3 = LocalTime.of(3,15,0);
        DayOfWeek[] days3 = {DayOfWeek.of(2), DayOfWeek.of(4)};
        Course class3 = new Course("HUMA 303 I","Christ Civ", "CHRISTIANITY AND CIVILIZATION", start3, end3, days3,"SHAL", "114", 31, 32);
        schedule.addEvent(class3);

        LocalTime start4 = LocalTime.of(11,0,0);
        LocalTime end4 = LocalTime.of(12,15,0);
        DayOfWeek[] days4 = {DayOfWeek.of(2), DayOfWeek.of(4)};
        Course class4 = new Course("COMP 244 A","Parallel", "PARALLEL COMPUTING", start4, end4, days4,"STEM", "376", 22, 22);
        schedule.addEvent(class4);
    }

    public static void main(String[] args) {
        //System.out.println(new App().getGreeting());

        //Display all courses in the schedule
    
        //Sample data to test displaying courses
        Schedule schedule = new Schedule("123");
        initSampleData(schedule);

        //Display courses as a list
        /*
        System.out.println("Printing " + schedule.getSemester() + " schedule\n------------------------------");
        for(Event event : schedule.events) {
            System.out.println(event);
        }
        System.out.println();
        */

        //Testing Calendar display
        Calendar calendar = new Calendar(schedule);
        System.out.println(calendar);
    }
}
