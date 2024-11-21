package com.example.contactly.exception;
import org.springframework.http.HttpStatus;

public class ExpiredJwtException extends JwtAuthenticationException {

    public ExpiredJwtException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);  // Set status code as UNAUTHORIZED
    }
}