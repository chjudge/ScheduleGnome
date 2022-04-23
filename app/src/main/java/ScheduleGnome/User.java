package ScheduleGnome;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class User {
    Map<String, Schedule> savedSchedules;
    private String username;
    private String password;
    private int id;
    private String major;   //TODO: add major functionality
    private int graduation_year; //TODO: add graduation year functionality

    public User(String username, String password){
        savedSchedules = new HashMap<>();
        this.username = username;
        this.password = password;

    }

    public User(int id, String username, String password, int graduation_year, String major){
        this.id = id;
        this.username = username;
        this.password = password;
        this.major = major;
        this.graduation_year = graduation_year;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword() { return password; }

    public Boolean checkPassword(String toCheck){
        return this.password.equals(toCheck);
    }

    public void compareSchedules(Map<String, Schedule> schedules){

    }

    public void deleteAccount(){

    }

    public Schedule addNewSchedule(String name, boolean isFall) {
        Schedule newSched = new Schedule(name, isFall);
        savedSchedules.put(name, newSched);
        return newSched;
    }

    public Schedule getSchedule(String name) {
        return savedSchedules.getOrDefault(name, null);
    }

    public Map<String, Schedule> getSavedSchedules() {
        return savedSchedules;
    }

    public String printScheduleNames() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String key : savedSchedules.keySet()) {
            sb.append(("Schedule " + i + ": " + key + "\n"));
            i++;
        }
        return sb.toString();
    }
}