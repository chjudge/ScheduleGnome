package ScheduleGnome;

import java.util.HashMap;
import java.util.Map;

public class User {
    Map<String, Schedule> savedSchedules;

    public User(String username, String password){
        savedSchedules = new HashMap<>();
    }

    public void compareSchedules(Map<String, Schedule> schedules){

    }

    public void createAccount(){

    }

    public void deleteAccount(){

    }

    public Schedule addNewSchedule(String name) {
        Schedule newSched = new Schedule(name);
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
