// I worked on the homework assignment alone, using only course materials.
/**
 * CourseDoesNotExistException class.
 *
 * @author farinazzahiri
 * @version 1.0
 */
public class CourseDoesNotExistException extends RuntimeException {
    /**
     * Exception definition.
     *
     * @param id String
     */
    public CourseDoesNotExistException(String id) {
        super(String.format("Course with ID %s does not exist", id));
    }
}
