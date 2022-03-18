package ScheduleGnome;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

public class Search {
    private Data data;
    private int NUM_COURSES;
    private String searched;
    private ArrayList<String> depts;
    // private ArrayList<String> professor; // Web scraper needed
    private ArrayList<Integer> creditHrs;
    private ArrayList<DayOfWeek[]> dates;
    private ArrayList<LocalTime> startTimes;
    private ArrayList<LocalTime> endTimes;
    private ArrayList<Match> results;

    // TODO: JavaFX buttons will call your Search class's setters to set filters
    public Search() {
        data = new Data();
        NUM_COURSES = data.courses.size();
        searched = null;
        depts = new ArrayList<>();
        //professor = new ArrayList<>();
        creditHrs = new ArrayList<>();
        dates = new ArrayList<>();
        startTimes = new ArrayList<>();
        endTimes = new ArrayList<>();
        results = new ArrayList<>();
    }

    // toString for results
    public String resultToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < results.size(); i++) {
            sb.append((i + " " + results.get(i).getCourse().toString() + "\n"));
        }
        return sb.toString();
    }

    public ArrayList<Match> querySearch() {
        applyFilters();
        applySearchedInput();
        results.sort(new MatchComparator());
        for (int i = results.size()-1; i>=0; i--) {
            Match result = results.get(i);
            if (result.getRating() > 0) {
                break;
            }
            results.remove(result);
        }
        return results;
    }

    // TODO: IFFY way to do this. Might need to rethink
    public ArrayList<Match> applySearchedInput() {
        if (!hasSearchedQuery()) return results;
        for (Match result : results) {
            Course crs = result.getCourse();
            int newRating = result.getRating();
            // TODO: This is just a very iffy way of doing this
            if (crs.getBuilding()!=null && searched.contains(crs.getBuilding().toLowerCase())) {
                result.addSimilarity(crs.getBuilding());
                newRating++;
            }
            // Check through course code matches
            for (String s : crs.getCourseCode().split(" ")) {
                if (searched.contains(" " + s.toLowerCase() + " ")) {
                    result.addSimilarity(s);
                    newRating++;
                }
            }
            // Check for keywords
            for (String s : crs.getTitle().split(" ")) {
                if (s.equals("")) continue;
                if (searched.contains(s.toLowerCase())) {
                    result.addSimilarity(s);
                    newRating++;
                }
            }
            result.setRating(newRating);
        }
        return results;
    }

    public ArrayList<Match> applyFilters() {
        // TODO: Somehow clear filters (here or as user interacts with it)
        results.clear(); // TODO: Temporary solution (could be permanent)
        if (hasFilters()) {
            for (int i = 0; i < NUM_COURSES; i++) {
                Course curr = data.courses.get(i);
                int rate = isMatch(curr);
                if (rate > 0) { // Add in if matches user query as well
                    Match newMatch = new Match(curr, rate);
                    results.add(newMatch);
                }
            }
        }
        else {
            for (int i = 0; i < NUM_COURSES; i++) {
                Course curr = data.courses.get(i);
                Match newMatch = new Match(curr, 0);
                results.add(newMatch);
            }
        }
        return results;
    }

    public int isMatch(Course crs) {
        int rating = 0;
        // Apply department filter
        DeptIf:
        if (!depts.isEmpty()) {
            for (String dept : depts) {
                if (crs.getCourseCode().contains(dept)) {
                    rating++; // Found a match
                    break DeptIf;
                }
            }
            return -1;
        }
        // Apply start time filter
        StartIf:
        if (!startTimes.isEmpty()) {
            for (LocalTime startTime : startTimes) {
                if (crs.getStartTime().equals(startTime)) {
                    rating++; // Found a match
                    break StartIf;
                }
            }
            return -1;
        }
        // Apply day of the week filter
        DatesIf:
        if (!dates.isEmpty()) {
            DayOfWeek[] crsDates = crs.getDates();
            int numDays = crsDates.length;
            for (DayOfWeek[] daysOfWeek : dates) {
                if (numDays == daysOfWeek.length) {
                    for (int i = 0; i < daysOfWeek.length; i++) {
                        if (crsDates[i].equals(daysOfWeek[i])) {
                            rating++; // Found a match
                            break DatesIf;
                        }
                    }
                }
            }
            return -1;
        }
        // TODO: Add credit hours check, professor, class level filters?
        return rating;
    }

    public Boolean hasFilters() {
        if (!depts.isEmpty()) return true;
        if (!startTimes.isEmpty()) return true;
        if (!dates.isEmpty()) return true;
        if (!creditHrs.isEmpty()) return true;
        return false;
    }

    public Boolean hasSearchedQuery() {
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
    }

    public ArrayList<String> getDepts() {
        return depts;
    }

    public void addDept(String dept) {
        this.depts.add(dept);
    }

    //public String getProfessor() {
    //    return professor;
    //}

    //public void setProfessor(String professor) {
    //    this.professor = professor;
    //}

    public ArrayList<Integer> getCreditHrs() { return creditHrs; }

    public void addCreditHrs(int creditHrs) { this.creditHrs.add(creditHrs); }

    public ArrayList<DayOfWeek[]> getDates() { return dates; }

    public void addDates(String dates) {
        DayOfWeek[] meets;
        if (dates == null)
            meets = null;
        else {
            String[] days = dates.split("");
            meets = new DayOfWeek[days.length];

            for (int i = 0; i < days.length; i++) {
                switch (days[i]) {
                    case "M":
                        meets[i] = DayOfWeek.of(1);
                        break;
                    case "T":
                        meets[i] = DayOfWeek.of(2);
                        break;
                    case "W":
                        meets[i] = DayOfWeek.of(3);
                        break;
                    case "R":
                        meets[i] = DayOfWeek.of(4);
                        break;
                    case "F":
                        meets[i] = DayOfWeek.of(5);
                        break;
                    default:
                        break;
                }
            }
        }
        this.dates.add(meets);
    }

    public ArrayList<LocalTime> getStartTimes() { return startTimes; }

    public void addStartTime(String startTime) {
        LocalTime start = null;
        try {
            start = startTime == null ? null : LocalTime.parse(startTime);
        } catch (java.time.format.DateTimeParseException ex) {
            startTime = "0" + startTime;
            start = startTime == null ? null : LocalTime.parse(startTime);
        }
        this.startTimes.add(start);
    }

    public ArrayList<LocalTime> getEndTimes() { return endTimes; }

    public void addEndTime(String endTime) {
        LocalTime end = null;
        try {
            end = endTime == null ? null : LocalTime.parse(endTime);
        } catch (java.time.format.DateTimeParseException ex) {
            endTime = "0" + endTime;
            end = endTime == null ? null : LocalTime.parse(endTime);
        }
        this.endTimes.add(end);
    }

    public ArrayList<Match> getResults() { return results; }

    public void setResults(ArrayList<Match> results) { this.results = results; }
}
