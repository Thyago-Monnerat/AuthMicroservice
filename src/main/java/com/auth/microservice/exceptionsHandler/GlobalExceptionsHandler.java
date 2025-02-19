package com.auth.microservice.exceptionsHandler;

import com.auth.microservice.exceptions.PasswordsUnmatchedException;
import com.auth.microservice.exceptions.UserAlreadyRegisteredException;
import com.auth.microservice.exceptions.UserNotRegisteredException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<String> handleUserAlreadyRegistered(UserAlreadyRegisteredException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UserNotRegisteredException.class)
    public ResponseEntity<String> handleUserNotRegistered(UserNotRegisteredException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PasswordsUnmatchedException.class)
    public ResponseEntity<String> handlePasswordsUnmatchedException(PasswordsUnmatchedException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<String> handleJwtCreation(JWTCreationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<String> handleJwtVerification(JWTCreationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
