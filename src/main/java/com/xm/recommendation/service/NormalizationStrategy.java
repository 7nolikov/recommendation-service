package com.xm.recommendation.service;

/**
 * Strategy for normalizing data.
 */
public enum NormalizationStrategy {

  /**
   * Normalize data using min-max normalization.
   */
  MIN_MAX,
  /**
   * Normalize data using z-score normalization.
   */
  Z_SCORE
}
