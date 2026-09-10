package org.schoolmanagement.schoolmanagement.exception;

public class IncorrectPasswordException extends BadRequestException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
