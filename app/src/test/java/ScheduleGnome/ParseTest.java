package ScheduleGnome;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class ParseTest {
    @Test
    void loadCourses() {
        ArrayList<Event> events = new ArrayList<Event>(); 
        ParseCourses parser = new ParseCourses();
        
        parser.readCourses(events);

        assertEquals(761, events.size());
    }

    @Test
    void correctCourses(){
        ArrayList<Event> events = new ArrayList<Event>(); 
        ParseCourses parser = new ParseCourses();
        
        parser.readCourses(events);
        String[] s = {"ACCT 202  C","PRIN OF ACCOUNT","PRINCIPLES OF ACCOUNTING II","9:00:00","9:50:00","MWF","HAL","306","35","42"};
        Course c = new Course(s);
        assertEquals(c, events.get(5));
    }
}
