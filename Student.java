// I worked on the homework assignment alone, using only course materials.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Student class.
 *
 * @author farinazzahiri
 * @version 1.0
 */

public class Student {
    private final String id;
    private Course[] takenCourses;
    private CourseOffering[] registrations;

    /**
     * getter for ID.
     *
     * @return a string
     */
    public String getID() {
        return this.id;
    }

    /**
     * getter for courses that have been taken.
     *
     * @return a course array
     */
    public Course[] getTakenCourses() {
        return this.takenCourses;
    }

    /**
     * getting all of the registrations.
     *
     * @return a course offering array
     */
    public CourseOffering[] getRegistrations() {
        return this.registrations;
    }

    /**
     * private constructor.
     *
     * @param id type String
     * @param takenCourses course array
     * @param registrations course offerings array
     */
    private Student(String id, Course[] takenCourses, CourseOffering[] registrations) {
        this.id = id;
        this.takenCourses = takenCourses;
        this.registrations = registrations;
    }

    /**
     * fromID method for student.
     *
      * @param id String
     * @return a Student
     * @throws FileNotFoundException file doesn't exist
     */
    public static Student fromID(String id) throws FileNotFoundException {
        Course[] takenCourses = new Course[0];
        CourseOffering[] registrations = new CourseOffering[0];
        File file = new File("student-" + id + ".txt");
        try (Scanner fileScan = new Scanner(file)) {
            if (fileScan.hasNextLine()) {
                String takenCoursesLine = fileScan.nextLine();
                String[] takenCourseIds = takenCoursesLine.split(" ");
                takenCourses = new Course[takenCourseIds.length];
                for (int i = 0; i < takenCourseIds.length; i++) {
                    takenCourses[i] = Course.fromID(takenCourseIds[i]);
                }
            }
            if (fileScan.hasNextLine()) {
                String registrationLine = fileScan.nextLine();
                String[] registrationIds = registrationLine.split(" ");
                registrations = new CourseOffering[registrationIds.length];
                for (int i = 0; i < registrations.length; i++) {
                    registrations[i] = CourseOffering.getCourseOffering(registrationIds[i]);
                }
            }
        } catch (FileNotFoundException e) {
            Student student = new Student(id, takenCourses, registrations);
            student.updateFile();
            return student;
        }
        return new Student(id, takenCourses, registrations);
    }

    /**
     * updating our file.
     *
      * @throws FileNotFoundException file usually wont exist
     */
    private void updateFile() throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(new File("student-" + this.id + ".txt"))) {
            for (int i = 0; i < this.takenCourses.length; i++) {
                if (i < takenCourses.length - 1) {
                    out.print(takenCourses[i].getID() + " ");
                } else {
                    out.print(takenCourses[i].getID());
                }
            }
            out.println();
            for (int i = 0; i < this.registrations.length; i++) {
                if (i < registrations.length - 1) {
                    out.print(registrations[i].getID() + " ");
                } else {
                    out.print(registrations[i].getID());
                }
            }
            out.println();
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    /**
     * overwriting equals method.
     *
     * @param other of object type
     * @return a boolean
     */
    public boolean equals(Object other) {
        if (!(other instanceof Student)) {
            return false;
        }
        Student studentOther = (Student) other;
        return this.id.equals(studentOther.id);
    }

    /**
     * check if they pass all courses.
     *
      * @throws FileNotFoundException file doesn't exist
     */
    public void passAllCourses() throws FileNotFoundException {
        Course[] backupTakenCourses = Arrays.copyOf(takenCourses, takenCourses.length);
        CourseOffering[] backupRegistrations = Arrays.copyOf(registrations, registrations.length);
        boolean success = false;
        try {
            takenCourses = Arrays.copyOf(takenCourses, takenCourses.length + registrations.length);
            int regIndex = 0;
            for (int i = 0; i < takenCourses.length; i++) {
                if (takenCourses[i] == null) {
                    takenCourses[i] = registrations[regIndex].getCourse();
                    regIndex++;
                }
            }
            registrations = new CourseOffering[0];
            Arrays.sort(takenCourses);
            updateFile();
            success = true;
        } finally {
            if (!success) {
                takenCourses = backupTakenCourses;
                registrations = backupRegistrations;
            }
        }
    }

    /**
     * updates the registration of the student.
     *
     * @param inputOfferings goes in as a course offering
     * @throws FileNotFoundException file doesn't exist
     * @throws UnsatisfiableRegistrationException class may be full or 0 seats
     */
    public void updateRegistration(CourseOffering[] inputOfferings) throws FileNotFoundException,
            UnsatisfiableRegistrationException {
        int totalCreditHours = 0;
        Course[] repeatedCourses = new Course[0];
        CourseOffering[] backupRegistrations = Arrays.copyOf(registrations, registrations.length);
        CourseOffering[] fullOfferings = new CourseOffering[0];
        CourseOffering[] removeOfferings = new CourseOffering[0];
        CourseOffering[] addOfferings = new CourseOffering[0];

        for (CourseOffering element : inputOfferings) {
            totalCreditHours += element.getCourse().getCreditHours();
        }
        if (totalCreditHours > 18) {
            throw new UnsatisfiableRegistrationException(this.id, "registration exceeds 18 credit hours");
        }
        for (CourseOffering element : inputOfferings) {
            boolean found = false;
            for (CourseOffering element2 : registrations) {
                if (element.equals(element2)) {
                    found = true;
                }
            }
            if (found) {
                repeatedCourses = Arrays.copyOf(repeatedCourses, repeatedCourses.length + 1);
                repeatedCourses[repeatedCourses.length - 1] = element.getCourse();
            } else {
                if (element.getOccupiedSeats() == element.getTotalSeats()) {
                    fullOfferings = Arrays.copyOf(fullOfferings, fullOfferings.length + 1);
                    fullOfferings[fullOfferings.length - 1] = element;
                } else {
                    addOfferings = Arrays.copyOf(addOfferings, addOfferings.length + 1);
                    addOfferings[addOfferings.length - 1] = element;
                }
            }
        }
        for (CourseOffering element : registrations) {
            boolean found = false;
            for (CourseOffering element2 : inputOfferings) {
                if (element.equals(element2)) {
                    found = true;
                }
            }
            if (!found) {
                removeOfferings = Arrays.copyOf(removeOfferings, removeOfferings.length + 1);
                removeOfferings[removeOfferings.length - 1] = element;
            }
        }
        if (repeatedCourses.length > 0) {
            Arrays.sort(repeatedCourses);
            String message = "courses already taken (";
            for (Course element : repeatedCourses) {
                message += element.getID() + ", ";
            }
            message = message.substring(0, message.length() - 2);
            message = message + ")";
            throw new UnsatisfiableRegistrationException(this.id, message);
        }
        if (fullOfferings.length > 0) {
            Arrays.sort(fullOfferings);
            String message = "courses full (";
            for (CourseOffering element : fullOfferings) {
                message += element.getID() + ", ";
            }
            message = message.substring(0, message.length() - 2);
            message += message + ")";
            throw new UnsatisfiableRegistrationException(this.id, message);
        }
        for (CourseOffering element : addOfferings) {
            element.addStudent();
        }
        for (CourseOffering element : removeOfferings) {
            element.removeStudent();
        }
        registrations = inputOfferings;
        try {
            CourseOffering.saveCourseOfferings();
            this.updateFile();
        } catch (FileNotFoundException e) {
            for (CourseOffering element : addOfferings) {
                element.removeStudent();
            }
            for (CourseOffering element : removeOfferings) {
                element.addStudent();
            }
            registrations = backupRegistrations;
            throw e;
        }
    }

    /**
     * have taken it already private method.
     *
      * @param otherOffering of course offering type
     * @return returns a boolean
     */
    private boolean haveTakenCourseOffering(CourseOffering otherOffering) {
        Course otherCourse = otherOffering.getCourse();
        for (Course element: takenCourses) {
            if (element.equals(otherCourse)) {
                return true;
            }
        }
        return false;
    }

    /**
     * if you have already registered.
     *
     * @param otherOffering course offering
     * @return return a boolean
     */
    private boolean haveRegisteredCourseOffering(CourseOffering otherOffering) {
        for (CourseOffering element : registrations) {
            if (element.equals(otherOffering)) {
                return true;
            }
        }
        return false;
    }


}
