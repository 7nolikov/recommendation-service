package com.xm.recommendationservice.middleware;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class RateLimiter {

  private final Bucket bucket;

  public RateLimiter() {
    Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
    this.bucket = Bucket.builder()
        .addLimit(limit)
        .build();
  }

  public boolean canAcquire() {
    return bucket.tryConsume(1);
  }
}
