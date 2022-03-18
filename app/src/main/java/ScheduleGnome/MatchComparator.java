package ScheduleGnome;

import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {
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
