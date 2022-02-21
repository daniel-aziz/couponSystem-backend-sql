package Execptions;

/**
 * this Exception is for when the login credentials of the user are incorrect
 */
public class InvalidLoginCredentialsException extends Exception {

    public InvalidLoginCredentialsException() {
        super("Username or Password are incorrect");
    }

    public InvalidLoginCredentialsException(String message) {
        super(message);
    }
}
