package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

/** DTO for crypto price extremes. */
@Builder
public record ExtremesDto(
    String symbol,
    BigDecimal oldestPrice,
    LocalDateTime oldestTimestamp,
    BigDecimal newestPrice,
    LocalDateTime newestTimestamp,
    BigDecimal minPrice,
    BigDecimal maxPrice) {

  /**
   * Convert a crypto price to a crypto price extremes DTO.
   */
  public static ExtremesDto fromCryptoPrice(CryptoPrice cryptoPrice) {
    return ExtremesDto.builder()
        .symbol(cryptoPrice.symbol())
        .oldestPrice(cryptoPrice.price())
        .oldestTimestamp(cryptoPrice.timestamp())
        .newestPrice(cryptoPrice.price())
        .newestTimestamp(cryptoPrice.timestamp())
        .minPrice(cryptoPrice.price())
        .maxPrice(cryptoPrice.price())
        .build();
  }

  /**
   * Convert a crypto price to a crypto price extremes DTO with a new min price.
   */
  public static ExtremesDto fromMinPrice(ExtremesDto source, BigDecimal minPrice) {
    return ExtremesDto.builder()
        .symbol(source.symbol())
        .oldestPrice(source.oldestPrice())
        .oldestTimestamp(source.oldestTimestamp())
        .newestPrice(source.newestPrice())
        .newestTimestamp(source.newestTimestamp())
        .minPrice(minPrice)
        .maxPrice(source.maxPrice())
        .build();
  }

  /**
   * Convert a crypto price to a crypto price extremes DTO with a new max price.
   */
  public static ExtremesDto fromMaxPrice(ExtremesDto source, BigDecimal maxPrice) {
    return ExtremesDto.builder()
        .symbol(source.symbol())
        .oldestPrice(source.oldestPrice())
        .oldestTimestamp(source.oldestTimestamp())
        .newestPrice(source.newestPrice())
        .newestTimestamp(source.newestTimestamp())
        .minPrice(source.minPrice())
        .maxPrice(maxPrice)
        .build();
  }

  /**
   * Convert a crypto price to a crypto price extremes DTO with a new min timestamp.
   */
  public static ExtremesDto fromMinTimestamp(
      ExtremesDto source, BigDecimal oldestPrice, LocalDateTime minTimestamp) {
    return ExtremesDto.builder()
        .symbol(source.symbol())
        .oldestPrice(oldestPrice)
        .oldestTimestamp(minTimestamp)
        .newestPrice(source.newestPrice())
        .newestTimestamp(source.newestTimestamp())
        .minPrice(source.minPrice())
        .maxPrice(source.maxPrice())
        .build();
  }

  /**
   * Convert a crypto price to a crypto price extremes DTO with a new max timestamp.
   */
  public static ExtremesDto fromMaxTimestamp(
      ExtremesDto source, BigDecimal newestPrice, LocalDateTime maxTimestamp) {
    return ExtremesDto.builder()
        .symbol(source.symbol())
        .oldestPrice(source.oldestPrice())
        .oldestTimestamp(source.oldestTimestamp())
        .newestPrice(newestPrice)
        .newestTimestamp(maxTimestamp)
        .minPrice(source.minPrice())
        .maxPrice(source.maxPrice())
        .build();
  }
}
