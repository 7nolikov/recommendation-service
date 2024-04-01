package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
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
  public List<CryptoPrice> normalize(
      List<CryptoPrice> cryptoPrices, NormalizationStrategy strategy) {
    return switch (strategy) {
      case MIN_MAX -> normalizeByMinMax(cryptoPrices);
      case Z_SCORE -> normalizeByZScore(cryptoPrices);
    };
  }

  /** Normalize data using min-max normalization. */
  private List<CryptoPrice> normalizeByMinMax(List<CryptoPrice> cryptoPrices) {
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

    return cryptoPrices.stream()
        .map(
            cryptoPrice ->
                CryptoPrice.builder()
                    .symbol(cryptoPrice.symbol())
                    .timestamp(cryptoPrice.timestamp())
                    .price(
                        (cryptoPrice
                            .price()
                            .subtract(minPrice)
                            .divide((maxPrice.subtract(minPrice)), RoundingMode.HALF_UP)))
                    .build())
        .toList();
  }

  /** Normalize data using Z-score normalization. */
  private List<CryptoPrice> normalizeByZScore(List<CryptoPrice> cryptoPrices) {
    BigDecimal mean =
        cryptoPrices.stream()
            .map(CryptoPrice::price)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(cryptoPrices.size()), RoundingMode.HALF_UP);

    BigDecimal variance =
        cryptoPrices.stream()
            .map(CryptoPrice::price)
            .map(price -> price.subtract(mean).pow(2))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(cryptoPrices.size()), RoundingMode.HALF_UP);

    BigDecimal standardDeviation = variance.sqrt(new MathContext(10, RoundingMode.HALF_UP));

    return cryptoPrices.stream()
        .map(
            cryptoPrice ->
                CryptoPrice.builder()
                    .symbol(cryptoPrice.symbol())
                    .timestamp(cryptoPrice.timestamp())
                    .price(
                        cryptoPrice
                            .price()
                            .subtract(mean)
                            .divide(standardDeviation, RoundingMode.HALF_UP))
                    .build())
        .toList();
  }
}
