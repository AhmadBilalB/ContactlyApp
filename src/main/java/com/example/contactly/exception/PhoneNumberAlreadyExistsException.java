package com.example.contactly.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PhoneNumberAlreadyExistsException extends RuntimeException {
    private final HttpStatus status;

    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

}
