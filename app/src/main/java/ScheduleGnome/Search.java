package ScheduleGnome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Search {
    private Data data;
    private int NUM_COURSES;
    private String searched;
    private String dept;
    // private String professor; // Web scraper needed
    private int creditHrs;
    private String startTime;
    private String endTime;
    private PriorityQueue<Match> results;

    // TODO: JavaFX buttons will call your Search class's setters to set filters
    public Search() {
        data = new Data();
        NUM_COURSES = data.courses.size();
        searched = null;
        dept = null;
        //professor = null;
        creditHrs = -1;
        startTime = null;
        endTime = null;
        results = new PriorityQueue<>(new MatchComparator());
    }

    public void PrintResults() {
        while (!results.isEmpty()) {
            Match result = results.remove();
            System.out.println(result.getCourse().toString());
        }
    }

    public PriorityQueue<Match> QuerySearch() {
        // TODO: Somehow clear filters (here or as user interacts with it)
        for (int i = 0; i < NUM_COURSES; i++) {
            Course curr = data.courses.get(i);
            int rate = isMatch(curr);
            if (rate > 0) { // Add in if matches user query as well
                Match newMatch = new Match(curr, rate);
                results.add(newMatch);
            }
        }
        return results;
    }

    public int isMatch(Course crs) {
        int rating = 0;
        if (dept != null && !crs.getCourseCode().contains(dept)) return -1; else rating++;
        if (startTime != null && !crs.getStartTime().equals(startTime)) return -1; else rating++;
        // TODO: Add credit hours check, professor, class level filters?
        // TODO: Add user input filtering
        return rating;
    }

    // GETTERS N' SETTERS

    public String getSearched() {
        return searched;
    }

    public void setSearched(String searched) {
        this.searched = searched;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    //public String getProfessor() {
    //    return professor;
    //}

    //public void setProfessor(String professor) {
    //    this.professor = professor;
    //}

    public int getCreditHrs() { return creditHrs; }

    public void setCreditHrs(int creditHrs) { this.creditHrs = creditHrs; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public PriorityQueue<Match> getResults() { return results; }

    public void setResults(PriorityQueue<Match> results) { this.results = results; }
}

class Match {
    private Course crs;
    private int rating; // Will be used for a comparator

    public Match(Course crs, int rating) {
        this.crs = crs;
        this.rating = rating;
    }

    public Course getCourse() {
        return crs;
    }

    public int getRating() {
        return rating;
    }
}

class MatchComparator implements Comparator<Match>{
    // Overriding compare()method of Comparator
    // for descending order of cgpa
    public int compare(Match m1, Match m2) {
        if (m1.getRating() < m2.getRating())
            return 1;
        else if (m1.getRating() > m2.getRating())
            return -1;
        return 0;
    }
}