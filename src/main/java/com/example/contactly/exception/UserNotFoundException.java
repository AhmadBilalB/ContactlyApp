package com.example.contactly.exception;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends JwtAuthenticationException {

    public UserNotFoundException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);  // Set status code as UNAUTHORIZED
    }
}
