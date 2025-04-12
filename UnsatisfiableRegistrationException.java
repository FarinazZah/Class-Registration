// I worked on the homework assignment alone, using only course materials.
/**
 * Unsatifiable registrationclass.
 *
 * @author farinazzahiri
 * @version 1.0
 */
public class UnsatisfiableRegistrationException extends Exception {
    /**
     * Error output.
     *
     * @param id String
     * @param reason String
     */
    public UnsatisfiableRegistrationException(String id, String reason) {
        super(String.format("Cannot fulfill registration for student %s: %s", id, reason));
    }
}
