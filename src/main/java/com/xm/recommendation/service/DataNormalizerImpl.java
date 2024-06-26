package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.NormalizedCryptoPrice;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/** A service that normalizes data. */
@Service
public class DataNormalizerImpl implements DataNormalizer {

  @Override
  public List<NormalizedCryptoPrice> normalize(
      List<CryptoPrice> cryptoPrices, NormalizationStrategy strategy) {
    if (cryptoPrices == null || cryptoPrices.isEmpty() || strategy == null) {
      throw new IllegalArgumentException("Crypto prices list or strategy is null or empty");
    }
    return switch (strategy) {
      case MIN_MAX -> normalizeByMinMax(cryptoPrices);
      case Z_SCORE -> normalizeByzScore(cryptoPrices);
    };
  }

  /** Normalize data using min-max normalization. */
  private List<NormalizedCryptoPrice> normalizeByMinMax(List<CryptoPrice> cryptoPrices) {
    BigDecimal minPrice =
        cryptoPrices.stream()
            .map(CryptoPrice::price)
            .min(Comparator.naturalOrder())
            .orElseThrow(NoSuchElementException::new);
    BigDecimal maxPrice =
        cryptoPrices.stream()
            .map(CryptoPrice::price)
            .max(Comparator.naturalOrder())
            .orElseThrow(NoSuchElementException::new);

    if (maxPrice.compareTo(minPrice) == 0) {
      return cryptoPrices.stream()
          .map(
              cryptoPrice ->
                  NormalizedCryptoPrice.builder()
                      .symbol(cryptoPrice.symbol())
                      .timestamp(cryptoPrice.timestamp())
                      .price(cryptoPrice.price())
                      .normalizedPrice(BigDecimal.ZERO)
                      .build())
          .toList();
    }

    return cryptoPrices.stream()
        .map(
            cryptoPrice ->
                NormalizedCryptoPrice.builder()
                    .symbol(cryptoPrice.symbol())
                    .timestamp(cryptoPrice.timestamp())
                    .price(cryptoPrice.price())
                    .normalizedPrice(
                        cryptoPrice
                            .price()
                            .subtract(minPrice)
                            .divide((maxPrice.subtract(minPrice)), 4, RoundingMode.HALF_UP))
                    .build())
        .toList();
  }

  /** Normalize data using Z-score normalization. */
  private List<NormalizedCryptoPrice> normalizeByzScore(List<CryptoPrice> cryptoPrices) {
    BigDecimal mean =
        cryptoPrices.stream()
            .map(CryptoPrice::price)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(
                BigDecimal.valueOf(cryptoPrices.size()), new MathContext(8, RoundingMode.HALF_UP));

    BigDecimal variance =
        cryptoPrices.stream()
            .map(CryptoPrice::price)
            .map(price -> price.subtract(mean).pow(2))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(
                BigDecimal.valueOf(cryptoPrices.size()), new MathContext(8, RoundingMode.HALF_UP));

    BigDecimal standardDeviation = variance.sqrt(new MathContext(8, RoundingMode.HALF_UP));

    return cryptoPrices.stream()
        .map(
            cryptoPrice ->
                NormalizedCryptoPrice.builder()
                    .symbol(cryptoPrice.symbol())
                    .timestamp(cryptoPrice.timestamp())
                    .price(cryptoPrice.price())
                    .normalizedPrice(
                        cryptoPrice
                            .price()
                            .subtract(mean)
                            .divide(standardDeviation, new MathContext(8, RoundingMode.HALF_UP)))
                    .build())
        .toList();
  }
}
