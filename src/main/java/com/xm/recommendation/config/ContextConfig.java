package com.xm.recommendation.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

/**
 * Configuration for the application context.
 */
@Configuration
@RequiredArgsConstructor
@EnableCaching
public class ContextConfig {

  /**
   * Configuration for the rate limiter.
   *
   * @return the bandwidth configuration
   */
  @Bean
  Bandwidth bandwidth() {
    return Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
  }

  /**
   * Configuration for the Redis cache.
   *
   * @return the cache configuration
   */
  @Bean
  public RedisCacheConfiguration cacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(60))
        .disableCachingNullValues()
        .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
  }
}
