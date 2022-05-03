package ScheduleGnome;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ParseTest {
    @Test
    void loadCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        ParseCourses parser = new ParseCourses();
        
        parser.readCourses(courses);

        assertEquals(1494, courses.size());
    }

}
