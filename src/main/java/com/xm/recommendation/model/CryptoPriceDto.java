package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

/**
 * DTO for crypto price.
 */
@Builder
public record CryptoPriceDto(LocalDateTime timestamp, String symbol, BigDecimal price) {

  /**
   * Convert a list of crypto prices to a list of crypto price DTOs.
   *
   * @param cryptoPrices the list of crypto prices
   * @return the list of crypto price DTOs
   */
  public static List<CryptoPriceDto> fromCryptoPrices(List<CryptoPrice> cryptoPrices) {
    return cryptoPrices.stream()
        .map(
            cryptoPrice ->
                CryptoPriceDto.builder()
                    .timestamp(cryptoPrice.timestamp())
                    .symbol(cryptoPrice.symbol())
                    .price(cryptoPrice.price())
                    .build())
        .toList();
  }
}
