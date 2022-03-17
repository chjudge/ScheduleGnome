package ScheduleGnome;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ScheduleTest {
    @Test
     void addCourse() {
        Schedule sc = new Schedule("f22");
        Course crs = new Course(new String[]{"COMP 314 A", "FOUN COMP SCI",
                "FOUNDATIONS OF COMPUTER SCIENCE", "10:00:00", "10:50:00", "MWF",
                "STEM", "326", "33", "39"});
        sc.addEvent(crs);

        assertEquals(1, sc.scheduleSize());
    }

    @Test
    void deleteCourse(){
        Schedule sc = new Schedule("s22");
        Course crs = new Course(new String[]{"COMP 314 A", "FOUN COMP SCI",
                "FOUNDATIONS OF COMPUTER SCIENCE", "10:00:00", "10:50:00", "MWF",
                "STEM", "326", "33", "39"});
        sc.addEvent(crs);
        sc.deleteEvent(crs);
        assertEquals(0, sc.scheduleSize());

    }
}
