package com.example.contactly.exception;
import org.springframework.http.HttpStatus;


public class InvalidJwtSignatureException extends JwtAuthenticationException {

    public InvalidJwtSignatureException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);  // Set status code as UNAUTHORIZED
    }
}