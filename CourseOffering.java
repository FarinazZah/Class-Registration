// I worked on the homework assignment alone, using only course materials.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
/**
 * CourseOffering class.
 *
 * @author farinazzahiri
 * @version 1.0
 */

public class CourseOffering implements Comparable<CourseOffering> {
    private final String id;
    private final Course course;
    private final String semester;
    private int occupiedSeats;
    private final int totalSeats;
    private static File courseOfferingsFile = null;
    private static CourseOffering[] courseOfferings = new CourseOffering[0];

    /**
     * private constructor.
     *
     * @param newId String
     * @param newCourse Course
     * @param newSemester String
     * @param newOccupiedSets int
     * @param newTotalSeats int
     */
    private CourseOffering(String newId, Course newCourse, String newSemester, int newOccupiedSets, int newTotalSeats) {
        this.id = newId;
        this.course = newCourse;
        this.semester = newSemester;
        this.occupiedSeats = newOccupiedSets;
        this.totalSeats = newTotalSeats;
    }

    /**
     * loads the offerings.
     *
     * @param inputFile of type File
     * @throws FileNotFoundException if file does not exist
     */
    public static void loadCourseOfferings(File inputFile) throws FileNotFoundException {
        courseOfferingsFile = inputFile;
        courseOfferings = new CourseOffering[0];
        Scanner fileScan1 = null;
        int count = 0;
        try {
            fileScan1 = new Scanner(inputFile);
            while (fileScan1.hasNextLine()) {
                fileScan1.nextLine();
                count++;
            }
            courseOfferings = new CourseOffering[count];
        } catch (FileNotFoundException e) {
            throw e;
        }
        try {
            Scanner fileScan2 = new Scanner(inputFile);
            int index = 0;
            while (fileScan2.hasNextLine()) {
                String line = fileScan2.nextLine();
                String[] parts = line.split(",");
                String id = parts[0];
                String courseID = parts[1];
                String semester = parts[2];
                int occupiedSeats = Integer.parseInt(parts[3]);
                int totalSeats = Integer.parseInt(parts[4]);
                Course course = Course.fromID(courseID); // Simplified for example WEIRD
                courseOfferings[index++] = new CourseOffering(id, course, semester, occupiedSeats, totalSeats);
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
        Arrays.sort(courseOfferings);
    }

    /**
     * save course offering method.
     *
      * @throws FileNotFoundException bc no file.
     * @throws IllegalStateException bc illegal state
     */
    public static void saveCourseOfferings() throws FileNotFoundException {
        // Check if courseOfferingsFile is null
        if (courseOfferingsFile == null) {
            throw new IllegalStateException("The course offerings are not loaded");
        }

        try (PrintWriter out = new PrintWriter(courseOfferingsFile)) {
            for (int i = 0; i < courseOfferings.length; i++) {
                CourseOffering offering = courseOfferings[i];
                // Format: id,courseID,semester,occupiedSeats,totalSeats
                out.print(offering.id + ",");
                out.print(offering.course.getID() + ","); // Assuming Course has a getCourseID method WEIRD
                out.print(offering.semester + ",");
                out.print(offering.occupiedSeats + ",");
                out.print(offering.totalSeats);
                if (i < courseOfferings.length - 1 || courseOfferings.length > 0) {
                    out.println();
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    /**
     * get all the course offerings.
     *
      * @param id String
      * @return a course offering
     * @throws CourseOfferingDoesNotExistException because it doesn't
      */
    public static CourseOffering getCourseOffering(String id) {
        int low = 0;
        int high = courseOfferings.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            CourseOffering midVal = courseOfferings[mid];
            int cmp = midVal.id.compareTo(id);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return midVal;
            }
        }
        throw new CourseOfferingDoesNotExistException(id);
    }

    /**
     * add student method.
     *
     * @throws IllegalStateException the course is full
      */
    public void addStudent() {
        if (this.occupiedSeats >= this.totalSeats) {
            throw new IllegalStateException("The course offering " + this.id + " is full");
        }
        this.occupiedSeats++;
    }

    /**
     * remove student method.
     *
     * @throws IllegalStateException if the offering is empty
      */
    public void removeStudent() {
        if (this.occupiedSeats == 0) {
            throw new IllegalStateException("The course offering " + this.id + " is empty");
        }
        this.occupiedSeats--;
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
     * getter for course.
     *
     * @return a course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * getter for semester.
     *
     * @return String
     */
    public String getSemester() {
        return semester;
    }

    /**
     * getter for occupiedSeats.
     *
     * @return an int value
     */
    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    /**
     * getter for total number of seats.
     *
     * @return an integer
     */

    public int getTotalSeats() {
        return totalSeats;
    }

    /**
     * Overwriting equals method.
     *
     * @param other object type
     * @return a boolean
     */
    public boolean equals(Object other) {
        if (other instanceof CourseOffering) {
            CourseOffering otherCourse = (CourseOffering) other;
            return this.id.equals(otherCourse.getID());
        }
        return false;
    }

    /**
     * compareTo method.
     *
     * @param other the object to be compared.
     * @return an int
     */
    public int compareTo(CourseOffering other) {
        if (this.id.compareTo(other.getID()) != 0) {
            return this.id.compareTo(other.getID());
        }
        return 0;
    }

}
