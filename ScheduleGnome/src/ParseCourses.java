import java.io.File;

public class ParseCourses {
    public static void main(String[] args) {
        final String COURSES_PATH = "data/courses.csv";

        File courses = new File(COURSES_PATH);

        System.out.println(courses.exists());
        
    }
}