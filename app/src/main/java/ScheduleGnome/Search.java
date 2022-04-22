package ScheduleGnome;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search {
    private DBOperator db;
    private String searched;
    private Map<String, String> filters;
    private ArrayList<Match> results;
    private boolean isFall;

    // TODO: JavaFX buttons will call your Search class's setters to set filters
    public Search(boolean isFall) {
        db = new DBOperator();
        searched = null;
        filters = new HashMap<>();
        results = new ArrayList<>();
        this.isFall = isFall;
    }

    // toString for results
    public String resultToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < results.size(); i++) {
            sb.append((i + " " + results.get(i).getCourse().toString() + "\n"));
        }
        return sb.toString();
    }

    public ArrayList<Course> querySearch() {
        results.clear();
        results = db.filterCourses(filters, isFall);
        System.out.println("AFTER FILTER");
        System.out.println(resultToString());
        applySearchedInput();
        System.out.println("AFTER SEARCHED");
        System.out.println(resultToString());
        results.sort(new MatchComparator());
        if (hasSearchedQuery()) {
            for (int i = results.size() - 1; i >= 0; i--) {
                Match result = results.get(i);
                if (result.getRating() > 6) {
                    break;
                }
                results.remove(result);
            }
        }
        return getResults();
    }



    public ArrayList<Match> applySearchedInput() {
        if (!hasSearchedQuery()) return results;
        for (Match result : results) {
            Course crs = result.getCourse();
            int newRating = result.getRating();

            for (String s : searched.split(" ")) {
                if (s.isEmpty()) continue;
                // Check for building

                if (s.equals(crs.getDept().toLowerCase())) {
                    result.addSimilarity(crs.getDept());
                    newRating += 3;
                }
                if (s.equals(crs.getNumber() + "")) {
                    result.addSimilarity(crs.getNumber() + "");
                    newRating += 3;
                }
                int i = 8;
                for(String t : crs.getTitle().split(" ")){
                    if(t.toLowerCase().startsWith(s)){
                        newRating += i;
                    }
                    i-=2;
                }
               if(crs.getSection().equals("A")){
                   newRating += 4;
               }
               if(crs.getDept().toLowerCase().equals(s)){
                   newRating += 2;
               }


              newRating += crs.getCapacity()/20;


            }
            result.setRating(newRating);
        }
        return results;
    }

    public boolean hasSearchedQuery() {
        if (searched == null) return false;
        if (searched.isBlank()) return false;
        return true;
    }

    // GETTERS N' SETTERS

    public String getSearched() {
        return searched;
    }

    public void setSearched(String searched) {
        this.searched = searched.toLowerCase();
        System.out.println("Search set to: " +this.searched);
    }

    public String getFilterOfName(String filterName) {
        return filters.getOrDefault(filterName, "ERROR");
    }

    public void setDept(String dept) {
        this.filters.put("department",dept);
    }

    public void setCreditHrs(String creditHrs) {
        filters.put("credits",creditHrs);
    }

    public void setDates(String dates) {
        filters.put("dates",dates);
    }

    public void setStartTime(String startTime) {
        filters.put("beginTime", startTime);
    }

    public void setEndTime(String endTime) {
        filters.put("endTime", endTime);
    }

    public void setProfessor(String professor) {
        filters.put("professor",professor);
    }

    public ArrayList<Course> getResults() {
        ArrayList<Course> out = new ArrayList<Course>();
        for (Match match : results) {
            out.add(match.getCourse());
        }
        return out;
    }

    public ArrayList<Course> getAllCourses() {
        return new ArrayList<>();
    }

    private void clearFilters(){
        filters.clear();
    }

    public DBOperator getDB() {
        return db;
    }
}
