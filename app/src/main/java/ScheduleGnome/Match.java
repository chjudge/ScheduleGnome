package ScheduleGnome;

import java.util.ArrayList;

public class Match {
    private final Course crs;
    private int rating; // Will be used for a comparator
    private ArrayList<String> similarities;

    public Match(Course crs, int rating) {
        this.crs = crs;
        this.rating = rating;
        this.similarities = new ArrayList<>();
    }

    public Course getCourse() {
        return crs;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<String> getSimilarities() {
        return similarities;
    }

    public void addSimilarity(String s) { similarities.add(s); }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(crs.getTitle()).append(": similarities => ").append(similarities.toString()).append(", Rating => ").append(rating);
        return sb.toString();
    }
}