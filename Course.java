// I worked on the homework assignment alone, using only course materials.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Course class.
 *
 * @author farinazzahiri
 * @version 1.0
 */

public class Course implements Comparable<Course> {
    private final String id;
    private final String name;
    private final int creditHours;

    private Course(String newId, String newName, int newCreditHours) {
        this.id = newId;
        this.name = newName;
        this.creditHours = newCreditHours;
    }

    /**
     * getter for ID.
     *
     * @return a String
     */
    public String getID() {
        return id;
    }

    /**
     * getter for name.
     *
     * @return a String
     */
    public String getName() {
        return name;
    }

    /**
     * getter for credit hours.
     *
     * @return an int
     */
    public int getCreditHours() {
        return creditHours;
    }

    /**
     * equals method.
     *
     * @param other of object type
     * @return a boolean
     */

    public boolean equals(Object other) {
        if (!(other instanceof Course)) {
            return false;
        }
        Course otherCourse = (Course) other;
        return this.id.equals(otherCourse.id);
    }

    /**
     * compareTo method.
     *
     * @param otherCourse the object to be compared.
     * @return an int value
     */
    public int compareTo(Course otherCourse) {
        return this.id.compareTo(otherCourse.id);
    }

    /**
     * course's fromID method.
     *
     * @param id String
     * @return a Course type
     * @throws CourseDoesNotExistException in this
     */
    public static Course fromID(String id) {
        String inputFileName = "course-" + id + ".txt";
        File fileIn = new File(inputFileName); // Creating the file itself as object to read
        Scanner fileScan = null; // Setting a Scanner type to be able to scan through file
        try {
            fileScan = new Scanner(fileIn); // Actually scan through file, make sure the file exists, so in try block
        } catch (FileNotFoundException e) { // If the file doesn't exist
            throw new CourseDoesNotExistException(id); // Then we want to throw one of our unchecked exceptions using
            // the given ID
        }
        String givenName = fileScan.nextLine(); // Use the "scanned" version of the file to get line 1, giving us name
        int givenCreditHours = Integer.valueOf(fileScan.nextLine());
        Course output = new Course(id, givenName, givenCreditHours);
        return output; // Return that new course
    }
}
