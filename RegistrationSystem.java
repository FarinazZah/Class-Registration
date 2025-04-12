// I worked on the homework assignment alone, using only course materials.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Registration System class.
 *
 * @author farinazzahiri
 * @version 1.0
 */

public class RegistrationSystem {
    /**
     * main method.
     *
     * @param args command line arguements.
     * @throws FileNotFoundException file doesn't exist
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String filename = args[0];
        File fileIn = new File(filename);
        try {
            CourseOffering.loadCourseOfferings(fileIn);
            while (true) {
                System.out.println("> ");
                String command = scanner.nextLine();
                String[] commandElements = command.split(" ");
                String commandWord = commandElements[0];
                String id = commandElements[1];
                Student ourStudent = Student.fromID(id);
                String[] courseOfferingsIDs;
                CourseOffering[] courseOfferingArray;
                boolean leave = false;
                try {
                    switch (commandWord) {
                        case "register":
                            courseOfferingsIDs = Arrays.copyOfRange(commandElements, 2, commandElements.length);
                            courseOfferingArray = new CourseOffering[courseOfferingsIDs.length];
                            for (int i = 0; i < courseOfferingsIDs.length; i++) {
                                courseOfferingArray[i] = CourseOffering.getCourseOffering(courseOfferingsIDs[i]);
                            }
                            ourStudent.updateRegistration(courseOfferingArray);
                            break;
                        case "pass":
                            ourStudent.passAllCourses();
                            break;
                        default:
                            leave = true;
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    if (leave) {
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
    }
}
