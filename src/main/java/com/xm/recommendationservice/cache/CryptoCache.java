package com.xm.recommendationservice.cache;

import com.xm.recommendationservice.model.CryptoPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CryptoCache {

  private final RedisTemplate<String, CryptoPrice> redisTemplate;

  public CryptoPrice get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void set(String key, CryptoPrice crypto) {
    redisTemplate.opsForValue().set(key, crypto);
  }
}
