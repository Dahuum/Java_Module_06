package fr.school42.exceptions;

public class AlreadyAuthenticatedException extends RuntimeException {
    public AlreadyAuthenticatedException(String message) {
        super(message);
    }
}
