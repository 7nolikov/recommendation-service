package com.xm.recommendation.middleware;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateLimiter {

  private final Bucket bucket;

  @Autowired
  public RateLimiter(Bandwidth bandwidth) {
    this.bucket = Bucket.builder()
        .addLimit(bandwidth)
        .build();
  }

  public boolean canAcquire() {
    return bucket.tryConsume(1);
  }
}
