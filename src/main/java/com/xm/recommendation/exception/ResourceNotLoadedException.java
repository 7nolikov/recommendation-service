package com.xm.recommendation.exception;

/**
 * Exception thrown when a resource could not be loaded.
 */
public class ResourceNotLoadedException extends RuntimeException{

  public ResourceNotLoadedException(String message) {
    super(message);
  }

  public ResourceNotLoadedException(String message, Throwable cause) {
    super(message, cause);
  }
}
