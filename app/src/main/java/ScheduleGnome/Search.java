package ScheduleGnome;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Search {
    private String searched;
    private Map<String, String> filters;
    private ArrayList<Match> results;
    private boolean isFall;

    // TODO: JavaFX buttons will call your Search class's setters to set filters
    public Search(boolean isFall) {
        searched = null;
        filters = new HashMap<>();
        results = new ArrayList<>();
        this.isFall = isFall;
    }

    // toString for results
    public String resultToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < results.size(); i++) {
            sb.append(i).append(" ").append(results.get(i).getCourse().toString()).append("\n");
        }
        return sb.toString();
    }

    public ArrayList<Course> querySearch() {
        results.clear();
        results = JavaFXApp.getDB().filterCourses(filters, isFall);
        System.out.println("AFTER FILTER");
        System.out.println(resultToString());
        applySearchedInput();
        System.out.println("AFTER SEARCHED");
        System.out.println(resultToString());
        results.sort(new MatchComparator());
        if (hasSearchedQuery()) {
            for (int i = results.size() - 1; i >= 0; i--) {
                Match result = results.get(i);
                if (result.getRating() > 0) {
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
                    newRating += 5;
                }
                String[] titles = crs.getTitle().split(" ");

                for(String t : crs.getTitle().toLowerCase().split(" ")){

                    if(crs.getTitle().toLowerCase().startsWith(t) && t.startsWith(s)){
                        newRating += 5;
                    }
                    else if (t.contains(s)) {
                        newRating += 1;
                    } //
                }
            }
            result.setRating(newRating);
        }
        return results;
    }

    public boolean hasSearchedQuery() {
        if (searched == null) return false;
        return !searched.isBlank();
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
}
