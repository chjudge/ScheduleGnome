package ScheduleGnome;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Course extends Event {
    private String courseCode;
    private String shortTitle;
    private String building;
    private String room;
    private int enrollment;
    private int capacity;

    public Course(String courseCode, String shortTitle, String longTitle, LocalTime start, LocalTime end,
            DayOfWeek[] dates, String building, String room, int enrollment, int capacity) {
        super(longTitle, start, end, dates);
        this.courseCode = courseCode;
        this.shortTitle = shortTitle;
        this.building = building;
        this.room = room;
        this.enrollment = enrollment;
        this.capacity = capacity;
    }

    public Course(String[] items) {
        super(items[2], items[3], items[4], items[5]);

        this.courseCode = items[0];
        this.shortTitle = items[1];
        this.building = items[6];
        this.room = items[7];
        this.enrollment = items[8] == null ? -1 : Integer.parseInt(items[8]);
        this.capacity = items[9] == null ? -1 : Integer.parseInt(items[9]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((building == null) ? 0 : building.hashCode());
        result = prime * result + capacity;
        result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
        result = prime * result + enrollment;
        result = prime * result + ((room == null) ? 0 : room.hashCode());
        result = prime * result + ((shortTitle == null) ? 0 : shortTitle.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        if (building == null) {
            if (other.building != null)
                return false;
        } else if (!building.equals(other.building))
            return false;
        if (capacity != other.capacity)
            return false;
        if (courseCode == null) {
            if (other.courseCode != null)
                return false;
        } else if (!courseCode.equals(other.courseCode))
            return false;
        if (enrollment != other.enrollment)
            return false;
        if (room == null) {
            if (other.room != null)
                return false;
        } else if (!room.equals(other.room))
            return false;
        if (shortTitle == null) {
            if (other.shortTitle != null)
                return false;
        } else if (!shortTitle.equals(other.shortTitle))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return String.format("%-55s%-20s\n%-55s%-20s%-20s%-20s", getTitle(), getStartTime() + " - " + getEndTime(), courseCode,
                getDatesString(), building + " " + room, enrollment + "/" + capacity);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(int enrollment) {
        this.enrollment = enrollment;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDept() {
        if (courseCode == null || courseCode.isEmpty())
            return "Error";
        String[] crsCode = courseCode.split(" ");
        String out = "output";
        for (int i = 0, j = 0; j < 1; i++) {
            if (crsCode[i].equals(""))
                continue;
            out = crsCode[i];
            j++;
        }
        return out;
    }

    public String getCode() {
        if (courseCode == null || courseCode.isEmpty())
            return "Error";
        String[] crsCode = courseCode.split(" ");
        String out = "output";
        for (int i = 0, j = 0; j < 2; i++) {
            if (crsCode[i].equals(""))
                continue;
            if (j == 1)
                out = crsCode[i];
            j++;
        }
        return out;
    }

}