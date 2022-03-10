import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Event {
    private int id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ArrayList<DayOfWeek> dates;

    public Event(int id, String title, LocalDateTime start, LocalDateTime end, ArrayList<DayOfWeek> dates){
        this.id = id;
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.dates = new ArrayList<DayOfWeek>(dates);
    }
}