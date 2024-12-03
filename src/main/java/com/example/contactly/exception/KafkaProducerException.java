package com.example.contactly.exception;

public class KafkaProducerException extends RuntimeException {
  private String errorCode;

  public KafkaProducerException(String message) {
    super(message);
  }

  public KafkaProducerException(String message, Throwable cause) {
    super(message, cause);
  }

  public KafkaProducerException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public KafkaProducerException(String errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
