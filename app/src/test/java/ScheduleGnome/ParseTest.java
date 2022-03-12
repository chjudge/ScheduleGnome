package ScheduleGnome;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class ParseTest {
    @Test
    void loadCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        ParseCourses parser = new ParseCourses();
        
        parser.readCourses(courses);

        assertEquals(761, courses.size());
    }

    @Test
    void correctCourses(){
        ArrayList<Course> courses = new ArrayList<Course>();
        ParseCourses parser = new ParseCourses();
        
        parser.readCourses(courses);
        String[] s = {"ACCT 202  C","PRIN OF ACCOUNT","PRINCIPLES OF ACCOUNTING II","9:00:00","9:50:00","MWF","HAL","306","35","42"};
        Course c = new Course(s);
        assertEquals(c, courses.get(5));
    }
}
