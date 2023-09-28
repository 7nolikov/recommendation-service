package com.xm.recommendation.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ContextConfig {

  @Bean
  Bandwidth bandwidth() {
    return Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
  }
}
