import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course extends Event {
    private String courseCode;
    private String shortTitle;
    private String building;
    private String room;
    private int enrollment;
    private int capacity;

    public Course(int id, String courseCode, String shortTitle, String longTitle, LocalDateTime start, LocalDateTime end,
            ArrayList<DayOfWeek> dates, String building, String room, int enrollment, int capacity) {
        super(id, longTitle, start, end, dates);
        this.courseCode = courseCode;
        this.shortTitle = shortTitle;
        this.building = building;
        this.room = room;
        this.enrollment = enrollment;
        this.capacity = capacity;
    }
}