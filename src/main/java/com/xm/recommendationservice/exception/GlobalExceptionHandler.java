package com.xm.recommendationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CryptoNotFoundException.class)
  public ResponseEntity<Object> handleCryptoNotFoundException(CryptoNotFoundException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CryptoServiceException.class)
  public ResponseEntity<Object> handleCryptoServiceException(CryptoServiceException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceCannotBeLoadedException.class)
  public ResponseEntity<Object> handleCryptoServiceException(ResourceCannotBeLoadedException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
