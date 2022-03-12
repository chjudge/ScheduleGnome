package ScheduleGnome;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class SearchTest {
    @Test
    void filtersForDept() {
        Search searchingTool = new Search();
        searchingTool.addDept("COMP 314");
        searchingTool.addStartTime("10:00:00");
        searchingTool.addDates("MWF");
        Course crs = new Course(new String[] { "COMP 314 A", "FOUN COMP SCI",
                "FOUNDATIONS OF COMPUTER SCIENCE", "10:00:00", "10:50:00", "MWF",
                "STEM", "326", "33", "39" });
        assertEquals(3, searchingTool.isMatch(crs));
    }

}
