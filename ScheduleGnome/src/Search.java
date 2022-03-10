
import java.util.ArrayList;

public class Search {
    // private Data data;
    private int NUM_COURSES;
    private String searched;
    private String dept;
    private String professor;
    private int creditHrs;
    private String startTime;
    private String endTime;
    private ArrayList<Match> results;

    public Search(String searched, String dept, String prof, int creditHrs, String startTime, ArrayList<Course> results){
    public Search() {
        // data = new Data();
        // NUM_COURSES = data.courses.size();
        NUM_COURSES = 10; // Place holder
        searched = null;
        dept = null;
        professor = null;
`       creditHrs = -1;
`       startTime = null;
        endTime = null;
        results = null;
    }

    public ArrayList<Match> QuerySearch() {
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
        // TODO: Check if crs is a match
        return 0;
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

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getCreditHrs() {
        return creditHrs;
    }

    void search(){
    public void setCreditHrs(int creditHrs) {
        this.creditHrs = creditHrs;
    }

    public String getStartTime() {
        return startTime;
    }

    void filter(){
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Match> getResults() {
        return results;
    }

    public void setResults(ArrayList<Match> results) {
        this.results = results;
    }
}

class Match {
    Course crs;
    int rating; // Will be used for a comparitive

    public Match(Course crs, int rating) {
        this.crs = crs;
        this.rating = rating;
    }

    public Course getCrs() {
        return crs;
    }

    public int getRating() {
        return rating;
    }
}