package Execptions;

/**
 * this Exception is for when the login user type is not supported (wrong enum)
 */
public class InvalidUserTypeException extends Exception {

    public InvalidUserTypeException() {
        super("User Type entered not Supported");
    }

    public InvalidUserTypeException(String message) {
        super(message);
    }
}
