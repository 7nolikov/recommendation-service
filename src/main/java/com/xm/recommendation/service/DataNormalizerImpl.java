package com.xm.recommendation.service;

import com.xm.recommendation.config.ConfigProperties;
import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.NormalizedCryptoPrice;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** A service that normalizes data. */
@Service
@RequiredArgsConstructor
public class DataNormalizerImpl implements DataNormalizer {

  private final ConfigProperties properties;

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
                      .normalizedPrice(
                          BigDecimal.ZERO.setScale(
                              properties.getPriceScale(), RoundingMode.HALF_UP))
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
                            .divide(
                                (maxPrice.subtract(minPrice)),
                                new MathContext(properties.getPriceScale(), RoundingMode.HALF_UP))
                            .setScale(properties.getPriceScale(), RoundingMode.HALF_UP))
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
                BigDecimal.valueOf(cryptoPrices.size()),
                new MathContext(properties.getPriceScale(), RoundingMode.HALF_UP))
            .setScale(properties.getPriceScale(), RoundingMode.HALF_UP);

    BigDecimal variance =
        cryptoPrices.stream()
            .map(CryptoPrice::price)
            .map(price -> price.subtract(mean).pow(2))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(
                BigDecimal.valueOf(cryptoPrices.size()),
                new MathContext(properties.getPriceScale(), RoundingMode.HALF_UP))
            .setScale(properties.getPriceScale(), RoundingMode.HALF_UP);


    if (variance.compareTo(BigDecimal.ZERO) == 0) {
      throw new ArithmeticException("Variance is zero, cannot perform Z-Score normalization.");
    }

    BigDecimal standardDeviation =
        variance.sqrt(new MathContext(properties.getPriceScale(), RoundingMode.HALF_UP));

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
                            .divide(
                                standardDeviation,
                                new MathContext(properties.getPriceScale(), RoundingMode.HALF_UP))
                            .setScale(properties.getPriceScale(), RoundingMode.HALF_UP))
                    .build())
        .toList();
  }
}
