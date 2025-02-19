package com.auth.microservice.exceptions;

public class PasswordsUnmatchedException extends RuntimeException {
    public PasswordsUnmatchedException(String message) {
        super(message);
    }
}
