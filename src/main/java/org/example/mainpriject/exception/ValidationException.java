package org.example.mainpriject.exception;

public class ValidationException extends CustomException {
    public ValidationException(String message) {
        super(message, 400);
    }
}