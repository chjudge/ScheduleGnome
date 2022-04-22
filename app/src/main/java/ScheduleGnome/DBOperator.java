package ScheduleGnome;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class DBOperator {
    private static Connection conn;

    public DBOperator()
    {
        // Fill in code here to initialize conn so it connects to the database
        // using the provided parameters
        conn = null;

        //Get a properties variable so we can pass the username and password to
        // the database.
        Properties info = new Properties();
        info.put("user", "schedulegnome");
        info.put("password", "gnomepassword");
        //connect to the database
        try {
            conn = DriverManager.getConnection("jdbc:mysql://108.52.164.126:3306/gnomedb", info);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Match> filterCourses(Map<String,String> filters, boolean isFall) {
        try {
            String sql = "select * from course where semester = ?";
            if (!filters.isEmpty()) sql += " and";
            for (String key : filters.keySet()) {
                if (filters.get(key) == null) continue;
                if (key.equals("beginTime")) {
                    sql += " " + key + " >= ? and";
                }
                else if (key.equals("endTime")) {
                    sql += " " + key + " <= ? and";
                }
                else {
                    sql += " " + key + " = ? and";
                }
            }
            if (!filters.isEmpty()) sql = sql.substring(0,sql.length()-4); // Remove final and
            System.out.println(sql);
            PreparedStatement filterQuery = conn.prepareStatement(sql);
            int i = 1;
            if (isFall) filterQuery.setString(i++,"Fall");
            else filterQuery.setString(i++,"Spring");
            for (String key : filters.keySet()) {
                if (key.contains("Time")) {
                    System.out.println("Time thing: " + filters.get(key));
                    filterQuery.setTime(i++,Time.valueOf(LocalTime.parse(filters.get(key))));
                }
                else if (key.equals("credits")) {
                    filterQuery.setInt(i++,Integer.getInteger(filters.get(key)));
                }
                else {
                    filterQuery.setString(i++,filters.get(key));
                }
            }
            System.out.println("After setting: " + filterQuery.toString());
            ResultSet result = filterQuery.executeQuery();
            ArrayList<Match> filteredResults = new ArrayList<>();
            while (result.next()) {
                filteredResults.add(new Match(new Course(result), 0));
            }
            filters.clear();
            return filteredResults;
        } catch (SQLException e) {
            e.printStackTrace();
            filters.clear();
            return null;
        }
    }

    public boolean insertCourse(Course crs) {
        try {
            PreparedStatement insertCrs = conn.prepareStatement(
                    "insert into course (year,semester,title,department," +
                            "number,section,credits,capacity,enrollment,dates,beginTime," +
                            "endTime,professor,comment) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );

            int i = 1;

            insertCrs.setString(i++,crs.getYear());
            insertCrs.setString(i++,crs.getSemester());
            insertCrs.setString(i++,crs.getTitle());
            insertCrs.setString(i++,crs.getDept());
            insertCrs.setInt(i++,crs.getNumber());
            insertCrs.setString(i++,crs.getSection());
            insertCrs.setInt(i++,crs.getCreditHours());
            insertCrs.setInt(i++,crs.getCapacity());
            insertCrs.setInt(i++,crs.getEnrollment());
            insertCrs.setString(i++,crs.getDatesString());
            insertCrs.setTime(i++,Time.valueOf(crs.getStartTime()));
            insertCrs.setTime(i++,Time.valueOf(crs.getEndTime()));
            insertCrs.setString(i++,crs.getProfessor());
            insertCrs.setString(i++,crs.getComments());

            int rows = insertCrs.executeUpdate();
            insertCrs.close();
            if (rows == 0) {
                System.out.println("Course was not inserted");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public ArrayList<String> getDistinctDepts() {
        ArrayList<String> depts = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("select distinct department from course");
            while (result.next()) {
                depts.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depts;

    }

    public boolean insertUser() {
        return true;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
