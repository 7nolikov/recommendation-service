package com.xm.recommendation.exception;

/**
 * Exception thrown when no data is available.
 */
public class NoDataException extends RuntimeException {

  public NoDataException(String noDataForTheDay) {
    super(noDataForTheDay);
  }
}
