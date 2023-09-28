package com.xm.recommendation.cache;

import com.xm.recommendation.model.CryptoPrice;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CryptoCache {

  private final RedisTemplate<String, List<CryptoPrice>> redisTemplate;

  public List<CryptoPrice> get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void set(String key, List<CryptoPrice> crypto) {
    redisTemplate.opsForValue().set(key, crypto);
  }
}
