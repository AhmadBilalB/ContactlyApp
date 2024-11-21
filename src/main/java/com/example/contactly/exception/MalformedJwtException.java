package com.example.contactly.exception;
import org.springframework.http.HttpStatus;

public class MalformedJwtException extends JwtAuthenticationException {

    public MalformedJwtException(String message) {
        super(message, HttpStatus.BAD_REQUEST);  // Set status code as BAD_REQUEST
    }
}
