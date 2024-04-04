package com.xm.recommendation.middleware;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A rate limiter that limits the number of requests that can be made.
 */
@Component
public class RateLimiter {

  private final Bucket bucket;

  /**
   * Create a rate limiter with a given bandwidth.
   *
   * @param bandwidth the bandwidth to use
   */
  @Autowired
  public RateLimiter(Bandwidth bandwidth) {
    this.bucket = Bucket.builder()
        .addLimit(bandwidth)
        .build();
  }

  /**
   * Check if a request can be acquired.
   *
   * @return true if the request can be acquired, false otherwise
   */
  public boolean canAcquire() {
    return bucket.tryConsume(1);
  }
}
