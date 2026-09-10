package org.schoolmanagement.schoolmanagement.exception;

public class EmailExistsException extends BadRequestException {
    public EmailExistsException(String message) {
        super(message);
    }
}
