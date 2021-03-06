package ScheduleGnome;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
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
            PreparedStatement filterQuery = conn.prepareStatement(sql);
            int i = 1;
            if (isFall) filterQuery.setString(i++,"Fall");
            else filterQuery.setString(i++,"Spring");
            for (String key : filters.keySet()) {
                if (key.contains("Time")) {
                    filterQuery.setTime(i++,Time.valueOf(LocalTime.parse(filters.get(key))));
                }
                else if (key.equals("credits")) {
                    filterQuery.setInt(i++,Integer.getInteger(filters.get(key)));
                }
                else {
                    filterQuery.setString(i++,filters.get(key));
                }
            }
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

    public boolean loadSchedules(User user) {
        try{
            PreparedStatement getSchedules = conn.prepareStatement("select * from schedules where user_id=?");
            int i=1;
            getSchedules.setInt(i++,user.getId());
            ResultSet result = getSchedules.executeQuery();
            while(result.next()) {
                int schedId = result.getInt(1);
                Schedule sched = user.addNewSchedule(result.getString(2), result.getBoolean(4));
                sched.setId(schedId);
                //Fill schedule with courses
                PreparedStatement getCourses = conn.prepareStatement("select * from scheduled_courses where schedule_id=?");
                int j=1;
                getCourses.setInt(j++,schedId);
                ResultSet courseResults = getCourses.executeQuery();
                while(courseResults.next()) {
                    //Get all the course information from the course table
                    PreparedStatement getCourseFromId = conn.prepareStatement("select * from course where id=?");
                    getCourseFromId.setInt(1, courseResults.getInt(2));
                    ResultSet courseFromId = getCourseFromId.executeQuery();

                    //Add the course to schedule
                    courseFromId.next();
                    sched.addEvent(new Course(courseFromId));
                }

                PreparedStatement getExtracurriculars = conn.prepareStatement("select * from scheduled_events where schedule_id=?");
                getExtracurriculars.setInt(1,schedId);
                ResultSet excResults = getExtracurriculars.executeQuery();
                while(excResults.next()) {
                    Extracurriculars exc = new Extracurriculars(excResults);
                    sched.addEvent(exc);
                }
            }
            getSchedules.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public ArrayList<String> getAllTitles(boolean isFall) {
        ArrayList<String> titles = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select distinct title from course where semester = ?");
            if (isFall) stmt.setString(1,"Fall");
            else stmt.setString(1,"Spring");
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                titles.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
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

    public boolean deleteEvent(Event event, Schedule schedule) {
        try{
            int rows;
            if(event instanceof Course) {
                Course course = (Course)event;
                PreparedStatement deleteCourse = conn.prepareStatement("delete from scheduled_courses  where schedule_id=? and course_id=?");
                int i = 1;
                deleteCourse.setInt(i++, schedule.getId());
                deleteCourse.setInt(i++, course.getId());

                rows = deleteCourse.executeUpdate();
                deleteCourse.close();
            }
            else {
                PreparedStatement deleteEvent = conn.prepareStatement("delete from scheduled_events  where schedule_id=? and title=?");
                int i=1;
                deleteEvent.setInt(i++, schedule.getId());
                deleteEvent.setString(i++, event.getTitle());

                rows = deleteEvent.executeUpdate();
                deleteEvent.close();
            }
            if(rows == 0) {
                return false;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertUser(User user) {
        try {
            PreparedStatement insertUser = conn.prepareStatement(
                    "insert into users (username, password_text, graduation_year) values (?,?,?)"
            );
             int i=1;
             insertUser.setString(i++, user.getUsername()); //Store usernames without case sensitivity
             insertUser.setString(i++, user.getPassword());
             insertUser.setInt(i++, 0); //TODO: make graduation year actually update

             int rows = insertUser.executeUpdate();
             insertUser.close();
             if (rows == 0) {
                 System.out.println("User was not inserted");
                 return false;
             }

             //Set user's ID
            PreparedStatement stmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
             ResultSet result = stmt.executeQuery();
             result.next();
             user.setId(result.getInt(1));
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateProfile(User user) {
        try {
            PreparedStatement setProfile = conn.prepareStatement(
                    "update users set graduation_year=?, major=? where id=?"
            );
            int i=1;
            setProfile.setInt(i++, user.getGraduationYear());
            setProfile.setString(i++, user.getMajor());
            setProfile.setInt(i++, user.getId());

            int rows = setProfile.executeUpdate();

            setProfile.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("select * from users");

            while(result.next()) {
                if(result != null) {
                    users.add(new User(
                            result.getInt(1),
                            result.getString(2),
                            result.getString(3),
                            result.getInt(4),
                            result.getString(5)
                    ));
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean addNewSchedule(Schedule schedule) {
        try {
            PreparedStatement insertSchedule = conn.prepareStatement(
                    "insert into schedules (schedule_name, user_id, isFall) values (?,?,?)"
            );
            int i=1;
            insertSchedule.setString(i++, schedule.getName()); //Store usernames without case sensitivity
            insertSchedule.setInt(i++, schedule.getUser().getId());
            insertSchedule.setBoolean(i++, schedule.isFall());


            int rows = insertSchedule.executeUpdate();
            insertSchedule.close();
            if (rows == 0) {
                System.out.println("Schedule was not inserted");
                return false;
            }
            else {
                PreparedStatement stmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                i=1;
                ResultSet result = stmt.executeQuery();

                if(result != null) {
                    result.next();
                    schedule.setId(result.getInt(1));
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveSchedule(Schedule schedule) {
        try {

            PreparedStatement clearScheduledCourses = conn.prepareStatement("delete from scheduled_courses where schedule_id=?");
            int i=1;
            clearScheduledCourses.setInt(i++,schedule.getId());
            clearScheduledCourses.executeUpdate();
            PreparedStatement clearScheduledEvents = conn.prepareStatement("delete from scheduled_events where schedule_id=?");
            i=1;
            clearScheduledEvents.setInt(i++,schedule.getId());
            clearScheduledEvents.executeUpdate();

            ArrayList<Event> events = new ArrayList<>();
            ArrayList<Course> courses = new ArrayList<>();

            ArrayList<Event> allEvents = schedule.getEvents();
            for(Event event : allEvents) {
                if(event instanceof Course) {
                    Course course = (Course) event;
                    courses.add(course);
                }
                else {
                    events.add(event);
                }

            }

            //Insert courses
            String courseSql = "insert into scheduled_courses (schedule_id, course_id) values ";
            for (Course course : courses) {
                courseSql += "(";
                courseSql += "?,?";
                courseSql += "), ";
            }
            courseSql = courseSql.substring(0,courseSql.length()-2);

            PreparedStatement insertCourses = conn.prepareStatement(courseSql);
            i = 1;

            for(Course course: courses) {
                insertCourses.setInt(i++, schedule.getId());
                insertCourses.setInt(i++, course.getId());
            }

            String eventSql = "insert into scheduled_events (schedule_id, title, dates, beginTime, endTime) values ";
            for(Event event: events) {
                eventSql += "(";
                eventSql += "?,?,?,?,?";
                eventSql += "), ";
            }
            eventSql = eventSql.substring(0,eventSql.length()-2);

            PreparedStatement insertEvents = conn.prepareStatement(eventSql);

            i=1;
            for(Event event : events) {
                insertEvents.setInt(i++, schedule.getId());
                insertEvents.setString(i++, event.getTitle());
                insertEvents.setString(i++, event.getDatesString());
                insertEvents.setTime(i++, Time.valueOf(event.getStartTime()));
                insertEvents.setTime(i++, Time.valueOf(event.getEndTime()));
            }

            if(courses.size() > 0) {
                int rows = insertCourses.executeUpdate();
            }
            if(events.size() > 0) {
                int rows = insertEvents.executeUpdate();
            }
            insertCourses.close();
            insertEvents.close();

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteSchedule(Schedule schedule) {
        //Remove courses from scheduled_courses
        //Remove events from scheduled_events
        //Remove schedule from schedules

        try {
            PreparedStatement deleteCourses = conn.prepareStatement("delete from scheduled_courses where schedule_id=?");
            deleteCourses.setInt(1,schedule.getId());

            PreparedStatement deleteEvents = conn.prepareStatement("delete from scheduled_events where schedule_id=?");
            deleteEvents.setInt(1,schedule.getId());

            PreparedStatement deleteSchedule = conn.prepareStatement("delete from schedules where id=?");
            deleteSchedule.setInt(1,schedule.getId());

            deleteCourses.executeUpdate();
            deleteCourses.executeUpdate();
            deleteSchedule.executeUpdate();

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkConnection(){
        try {
            return conn.isValid(2);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
