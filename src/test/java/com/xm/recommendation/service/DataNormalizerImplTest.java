package com.xm.recommendation.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

import com.xm.recommendation.config.ConfigProperties;
import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.CsvToCryptoPriceList;
import com.xm.recommendation.model.NormalizedCryptoPrice;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Data normalizer tests")
class DataNormalizerImplTest {

  @InjectMocks private DataNormalizerImpl dataNormalizer;
  @Mock private ConfigProperties properties;

  @Nested
  @DisplayName("Should normalize data with different strategies")
  class shouldNormalizeDataWithDifferentStrategies {

    @Test
    @DisplayName("Should normalize data using min-max normalization")
    void shouldNormalizeDataUsingMinMaxNormalization() {
      when(properties.getPriceScale()).thenReturn(6);
      List<CryptoPrice> cryptoPrices =
          Arrays.asList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(100))
                  .build(),
              CryptoPrice.builder()
                  .symbol("ETH")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(200))
                  .build(),
              CryptoPrice.builder()
                  .symbol("LTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(300))
                  .build());

      List<NormalizedCryptoPrice> normalizedCryptoPrices =
          dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX);

      assertThat(normalizedCryptoPrices).hasSize(3);
      assertThat(normalizedCryptoPrices)
          .extracting(NormalizedCryptoPrice::normalizedPrice)
          .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
          .containsExactly(BigDecimal.ZERO, BigDecimal.valueOf(0.5), BigDecimal.ONE);
    }

    @Test
    @DisplayName("Should normalize data using Z-score normalization")
    void shouldNormalizeDataUsingZScoreNormalization() {
      when(properties.getPriceScale()).thenReturn(6);
      List<CryptoPrice> cryptoPrices =
          Arrays.asList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(100))
                  .build(),
              CryptoPrice.builder()
                  .symbol("ETH")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(200))
                  .build(),
              CryptoPrice.builder()
                  .symbol("LTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(300))
                  .build());

      List<NormalizedCryptoPrice> normalizedCryptoPrices =
          dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.Z_SCORE);

      assertThat(normalizedCryptoPrices).hasSize(3);
      assertThat(normalizedCryptoPrices.get(0).normalizedPrice())
          .isCloseTo(BigDecimal.valueOf(-1.224744871), within(BigDecimal.valueOf(0.0001)));
      assertThat(normalizedCryptoPrices.get(1).normalizedPrice())
          .isCloseTo(BigDecimal.ZERO, within(BigDecimal.valueOf(0.0001)));
      assertThat(normalizedCryptoPrices.get(2).normalizedPrice())
          .isCloseTo(BigDecimal.valueOf(1.224744871), within(BigDecimal.valueOf(0.0001)));
    }
  }

  @Nested
  @DisplayName("Should normalize data when border cases")
  class shouldNormalizeDataWhenBorderCases {

    @Test
    @DisplayName("Should normalize data when there is only one crypto price")
    void shouldNormalizeDataWhenThereIsOnlyOneCryptoPrice() {
      List<CryptoPrice> cryptoPrices =
          Collections.singletonList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(100))
                  .build());

      List<NormalizedCryptoPrice> normalizedCryptoPrices =
          dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX);

      assertThat(normalizedCryptoPrices).hasSize(1);
      assertThat(normalizedCryptoPrices.get(0).normalizedPrice()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should normalize data when crypto prices are negative")
    void shouldNormalizeDataWhenCryptoPricesAreNegative() {
      when(properties.getPriceScale()).thenReturn(6);
      List<CryptoPrice> cryptoPrices =
          Arrays.asList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(-100))
                  .build(),
              CryptoPrice.builder()
                  .symbol("ETH")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(-200))
                  .build(),
              CryptoPrice.builder()
                  .symbol("LTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(-300))
                  .build());

      List<NormalizedCryptoPrice> normalizedCryptoPrices =
          dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX);

      assertThat(normalizedCryptoPrices).hasSize(3);
      assertThat(normalizedCryptoPrices.get(0).normalizedPrice())
          .isCloseTo(BigDecimal.ONE, within(BigDecimal.valueOf(0.0001)));
      assertThat(normalizedCryptoPrices.get(1).normalizedPrice())
          .isCloseTo(BigDecimal.valueOf(0.5), within(BigDecimal.valueOf(0.0001)));
      assertThat(normalizedCryptoPrices.get(2).normalizedPrice())
          .isCloseTo(BigDecimal.ZERO, within(BigDecimal.valueOf(0.0001)));
    }

    @Test
    @DisplayName("Should normalize data when crypto price is zero")
    void shouldNormalizeDataWhenCryptoPriceIsZero() {
      List<CryptoPrice> cryptoPrices =
          Collections.singletonList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.ZERO)
                  .build());

      List<NormalizedCryptoPrice> normalizedCryptoPrices =
          dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX);

      assertThat(normalizedCryptoPrices).hasSize(1);
      assertThat(normalizedCryptoPrices.get(0).normalizedPrice()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should normalize data when all crypto prices are the same")
    void shouldNormalizeDataWhenAllCryptoPricesAreTheSame() {
      List<CryptoPrice> cryptoPrices =
          Arrays.asList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(100))
                  .build(),
              CryptoPrice.builder()
                  .symbol("ETH")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(100))
                  .build(),
              CryptoPrice.builder()
                  .symbol("LTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(100))
                  .build());

      List<NormalizedCryptoPrice> normalizedCryptoPrices =
          dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX);

      assertThat(normalizedCryptoPrices).hasSize(3);
      assertThat(normalizedCryptoPrices.get(0).normalizedPrice()).isEqualTo(BigDecimal.ZERO);
      assertThat(normalizedCryptoPrices.get(1).normalizedPrice()).isEqualTo(BigDecimal.ZERO);
      assertThat(normalizedCryptoPrices.get(2).normalizedPrice()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should normalize data when crypto price is negative")
    void shouldNormalizeDataWhenCryptoPriceIsNegative() {
      List<CryptoPrice> cryptoPrices =
          Collections.singletonList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(-100))
                  .build());

      List<NormalizedCryptoPrice> normalizedCryptoPrices =
          dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX);

      assertThat(normalizedCryptoPrices).hasSize(1);
      assertThat(normalizedCryptoPrices.get(0).normalizedPrice()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should normalize data when crypto price is extremely large")
    void shouldNormalizeDataWhenCryptoPriceIsExtremelyLarge() {
      List<CryptoPrice> cryptoPrices =
          Collections.singletonList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(new BigDecimal("1E+12"))
                  .build());

      List<NormalizedCryptoPrice> normalizedCryptoPrices =
          dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX);

      assertThat(normalizedCryptoPrices).hasSize(1);
      assertThat(normalizedCryptoPrices.get(0).normalizedPrice()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should normalize data when crypto price is extremely small")
    void shouldNormalizeDataWhenCryptoPriceIsExtremelySmall() {
      List<CryptoPrice> cryptoPrices =
          Collections.singletonList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(new BigDecimal("1E-12"))
                  .build());

      List<NormalizedCryptoPrice> normalizedCryptoPrices =
          dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX);

      assertThat(normalizedCryptoPrices).hasSize(1);
      assertThat(normalizedCryptoPrices.get(0).normalizedPrice()).isEqualTo(BigDecimal.ZERO);
    }
  }

  @Nested
  @DisplayName("Should throw exception")
  class shouldThrowException {

    @Test
    @DisplayName("Should throw exception when crypto prices list is empty")
    void shouldThrowExceptionWhenCryptoPricesListIsEmpty() {
      List<CryptoPrice> cryptoPrices = List.of();

      assertThatThrownBy(
              () -> dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Crypto prices list or strategy is null or empty");
    }

    @Test
    @DisplayName("Should throw exception when crypto prices list is null")
    void shouldThrowExceptionWhenCryptoPricesListIsNull() {
      assertThatThrownBy(() -> dataNormalizer.normalize(null, NormalizationStrategy.MIN_MAX))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should throw exception when normalization strategy is null")
    void shouldThrowExceptionWhenNormalizationStrategyIsNull() {
      List<CryptoPrice> cryptoPrices =
          Collections.singletonList(
              CryptoPrice.builder()
                  .symbol("BTC")
                  .timestamp(OffsetDateTime.now())
                  .price(BigDecimal.valueOf(100))
                  .build());

      assertThatThrownBy(() -> dataNormalizer.normalize(cryptoPrices, null))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("Should throw exception when crypto price values are null")
    @CsvSource({
        "null",
        "null, null, null",
        "BTC 50000 2024-04-01T12:00:00Z, null, ETH 3000 2024-04-01T12:05:00Z",
        "ETH 3000 2024-04-01T12:05:00Z, BTC 50000 2024-04-01T12:00:00Z, null",
        "null, ETH 3000 2024-04-01T12:05:00Z, BTC 50000 2024-04-01T12:00:00Z, null",
    })
    void shouldThrowExceptionWhenCryptoPriceIsNull(@CsvToCryptoPriceList List<CryptoPrice> cryptoPrices) {

      assertThatThrownBy(
              () -> dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX))
          .isInstanceOf(NullPointerException.class);
    }
  }
}
