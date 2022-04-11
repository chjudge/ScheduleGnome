package ScheduleGnome;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class DBOperator {
    private static Connection conn;

    public DBOperator(String username, String password, String schema)
        throws SQLException
    {
        // Fill in code here to initialize conn so it connects to the database
        // using the provided parameters
        conn = null;

        //Get a properties variable so we can pass the username and password to
        // the database.
        Properties info = new Properties();
        info.put("user", username);
        info.put("password", password);
        //connect to the database
        conn = DriverManager.getConnection("jdbc:mysql://GNOMEDBS/" + schema, info);

    }

    public ArrayList<Match> filterCourses(Map<String,String> filters) {
        try {
            String sql = "select * from Course";
            if (filters.isEmpty()) sql += " where";
            for (String key : filters.keySet()) {
                if (key.equals("startTime")) {
                    sql += " " + key + " >= ? and";
                }
                else if (key.equals("endTime")) {
                    sql += " " + key + " <= ? and";
                }
                else {
                    sql += " " + key + " = ? and";
                }
            }
            sql = sql.substring(0,sql.length()-4); // Remove final and
            PreparedStatement filterQuery = conn.prepareStatement(sql);
            int i = 1;
            for (String key : filters.keySet()) {
                if (key.contains("Time")) {
                    filterQuery.setTime(i++,Time.valueOf(filters.get(key)));
                }
                else if (key.equals("creditHrs")) {
                    filterQuery.setInt(i++,Integer.getInteger(filters.get(key)));
                }
                else {
                    filterQuery.setString(i++,filters.get(key));
                }
            }
            ResultSet result = filterQuery.executeQuery();
            ArrayList<Match> filteredResults = new ArrayList<>();
            while (result.next()) {
                // TODO: Course constructor from ResultSet
                filteredResults.add(new Match(new Course(result), 0));
            }
            return filteredResults;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertCourse(Course crs) {
        try {
            PreparedStatement insertCrs = conn.prepareStatement(
                    "insert into Course (id,year,semester,title,department," +
                            "number,section,credits,capacity,enrollment,dates,begin," +
                            "end,professor,comment) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );
            Statement stmt = conn.createStatement();
            ResultSet maxID = stmt.executeQuery("select max(ID) from Product");
            if (!maxID.next()) {
                System.out.println("Could not retrieve maximum id");
                return false;
            }
            int id = maxID.getInt(1) + 1;
            int i = 1;

            insertCrs.setInt(i++,id);
            insertCrs.setString(i++,crs.year);
            insertCrs.setString(i++,crs.semester);
            insertCrs.setString(i++,crs.title);
            insertCrs.setString(i++,crs.getDept());
            insertCrs.setInt(i++,crs.number);
            insertCrs.setString(i++,crs.section);
            insertCrs.setInt(i++,crs.credits);
            insertCrs.setInt(i++,crs.capacity);
            insertCrs.setInt(i++,crs.getEnrollment());
            insertCrs.setString(i++,crs.getDates());
            insertCrs.setTime(i++,Time.valueOf(crs.getStartTime()));
            insertCrs.setTime(i++,Time.valueOf(crs.getEndTime()));
            insertCrs.setString(i++,crs.professor);
            insertCrs.setDate(i++,crs.comment);

            int rows = insertCrs.executeUpdate();
            if (rows == 0) {
                System.out.println("Product was not inserted");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean insertUser() {
        return true;
    }

}
