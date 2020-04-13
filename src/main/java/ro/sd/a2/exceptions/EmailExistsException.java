package ro.sd.a2.exceptions;

public class EmailExistsException extends Exception {
    public EmailExistsException(String message) {
        super(message);
    }
}
