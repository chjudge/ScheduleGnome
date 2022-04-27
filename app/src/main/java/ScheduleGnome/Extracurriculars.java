package ScheduleGnome;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class Extracurriculars extends Event {
    public Extracurriculars(String title, LocalTime start, LocalTime end, String dates){
        super(title,start,end,dates);
    }

    public Extracurriculars(ResultSet result) throws SQLException {
        super(result.getString(2), result.getObject(4, LocalTime.class),
                result.getObject(5, LocalTime.class), result.getString(3));
    }

    void changeTime(double newTime){

    }

    void changeDate(DayOfWeek date){

    }

   void changeFlexibility(Boolean newFlex) {

    }

    void changeName(String newName){

    }

    void copyExtracurricular(){

    }
}
