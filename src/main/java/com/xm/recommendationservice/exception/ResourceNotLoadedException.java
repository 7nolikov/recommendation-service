package com.xm.recommendationservice.exception;

public class ResourceNotLoadedException extends RuntimeException{

  public ResourceNotLoadedException(String message) {
    super(message);
  }

  public ResourceNotLoadedException(String message, Throwable cause) {
    super(message, cause);
  }
}
