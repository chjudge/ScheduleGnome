package ScheduleGnome;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class SearchTest {

    // isMatch() Tests

    @Test
    void allMatches() {
        Search searchingTool = new Search();
        searchingTool.addDept("COMP 314");
        searchingTool.addStartTime("10:00:00");
        searchingTool.addDates("MWF");
        Course crs = new Course(new String[] { "COMP 314 A", "FOUN COMP SCI",
                "FOUNDATIONS OF COMPUTER SCIENCE", "10:00:00", "10:50:00", "MWF",
                "STEM", "326", "33", "39" });
        assertEquals(3, searchingTool.isMatch(crs));
    }
    @Test
    void oneMatchOneNot() {
        Search searchingTool = new Search();
        searchingTool.addDept("COMP 314");
        searchingTool.addStartTime("11:00:00");
        Course crs = new Course(new String[] { "COMP 314 A", "FOUN COMP SCI",
                "FOUNDATIONS OF COMPUTER SCIENCE", "10:00:00", "10:50:00", "MWF",
                "STEM", "326", "33", "39" });
        assertEquals(-1, searchingTool.isMatch(crs));
    }
    @Test
    void noFilters() {
        Search searchingTool = new Search();
        Course crs = new Course(new String[] { "COMP 314 A", "FOUN COMP SCI",
                "FOUNDATIONS OF COMPUTER SCIENCE", "10:00:00", "10:50:00", "MWF",
                "STEM", "326", "33", "39" });
        assertEquals(0, searchingTool.isMatch(crs));
    }

    @Test
    void multiFiltered() {
        Search searchingTool = new Search();
        searchingTool.addStartTime("10:00:00");
        searchingTool.addStartTime("11:00:00");
        searchingTool.addDates("TR");
        searchingTool.addDates("MWF");
        Course crs = new Course(new String[] { "COMP 314 A", "FOUN COMP SCI",
                "FOUNDATIONS OF COMPUTER SCIENCE", "10:00:00", "10:50:00", "MWF",
                "STEM", "326", "33", "39" });
        assertEquals(2, searchingTool.isMatch(crs));
    }

    //

    @Test
    void onlySearchQuery() {
        Search searchingTool = new Search();
        searchingTool.setSearched("coMpUter");
        Course crs = new Course(new String[] { "COMP 314 A", "FOUN COMP SCI",
                "FOUNDATIONS OF COMPUTER SCIENCE", "10:00:00", "10:50:00", "MWF",
                "STEM", "326", "33", "39" });
        int size = searchingTool.querySearch().size();
        assertEquals(23, size);
    }

}
