// I worked on the homework assignment alone, using only course materials.
/**
 * CourseOfferingDoesntExist class.
 *
 * @author farinazzahiri
 * @version 1.0
 */
public class CourseOfferingDoesNotExistException extends RuntimeException {
    /**
     * Exception definition.
     *
     * @param id String
     */
    public CourseOfferingDoesNotExistException(String id) {
        super(String.format("Course offering with ID %s does not exist", id));
    }
}

