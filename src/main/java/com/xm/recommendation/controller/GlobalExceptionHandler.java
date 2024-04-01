package com.xm.recommendation.controller;

import com.xm.recommendation.exception.CryptoNotFoundException;
import com.xm.recommendation.exception.CryptoServiceException;
import com.xm.recommendation.exception.ResourceNotLoadedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 */
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

  @ExceptionHandler(ResourceNotLoadedException.class)
  public ResponseEntity<Object> handleCryptoServiceException(ResourceNotLoadedException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
