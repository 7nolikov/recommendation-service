package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.Builder;

/** DTO for crypto price. */
@Builder
public record CryptoPriceDto(
    String symbol, BigDecimal price, OffsetDateTime timestamp, BigDecimal normalizedPrice) {

  /**
   * Convert a crypto price to a crypto price DTO.
   *
   * @param normalizedCryptoPrice the normalized crypto price
   * @return the crypto price DTO
   */
  public static Optional<CryptoPriceDto> fromNormalizedCryptoPrices(
      Optional<NormalizedCryptoPrice> normalizedCryptoPrice) {
    return normalizedCryptoPrice.map(
        cryptoPrice ->
            CryptoPriceDto.builder()
                .timestamp(cryptoPrice.timestamp())
                .symbol(cryptoPrice.symbol())
                .price(cryptoPrice.price())
                .normalizedPrice(cryptoPrice.normalizedPrice())
                .build());
  }

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

  /**
   * Convert a list of normalized crypto prices to a list of crypto price DTOs.
   *
   * @param normalizedAndSortedPrices the list of normalized crypto prices
   * @return the list of crypto price DTOs
   */
  public static List<CryptoPriceDto> fromNormalizedCryptoPrices(
      List<NormalizedCryptoPrice> normalizedAndSortedPrices) {
    return normalizedAndSortedPrices.stream()
        .map(
            normalizedCryptoPrice ->
                CryptoPriceDto.builder()
                    .timestamp(normalizedCryptoPrice.timestamp())
                    .symbol(normalizedCryptoPrice.symbol())
                    .price(normalizedCryptoPrice.price())
                    .normalizedPrice(normalizedCryptoPrice.normalizedPrice())
                    .build())
        .toList();
  }
}
