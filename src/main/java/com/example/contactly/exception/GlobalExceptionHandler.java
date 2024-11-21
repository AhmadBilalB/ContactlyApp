package com.example.contactly.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.contactly.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handlePhoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleExpiredJwtException(ExpiredJwtException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleMalformedJwtException(MalformedJwtException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(InvalidJwtSignatureException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidJwtSignatureException(InvalidJwtSignatureException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponseDTO("An unexpected error occurred: " + ex.getMessage()));
    }
}
