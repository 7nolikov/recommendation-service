package com.xm.recommendation.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.NormalizedCryptoPrice;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataNormalizerImplTest {

  @InjectMocks private DataNormalizerImpl dataNormalizer;

  @Test
  @DisplayName("Should normalize data using min-max normalization")
  void shouldNormalizeDataUsingMinMaxNormalization() {
    List<CryptoPrice> cryptoPrices =
        Arrays.asList(
            CryptoPrice.builder()
                .symbol("BTC")
                .timestamp(LocalDateTime.now())
                .price(BigDecimal.valueOf(100))
                .build(),
            CryptoPrice.builder()
                .symbol("ETH")
                .timestamp(LocalDateTime.now())
                .price(BigDecimal.valueOf(200))
                .build(),
            CryptoPrice.builder()
                .symbol("LTC")
                .timestamp(LocalDateTime.now())
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
    List<CryptoPrice> cryptoPrices =
        Arrays.asList(
            CryptoPrice.builder()
                .symbol("BTC")
                .timestamp(LocalDateTime.now())
                .price(BigDecimal.valueOf(100))
                .build(),
            CryptoPrice.builder()
                .symbol("ETH")
                .timestamp(LocalDateTime.now())
                .price(BigDecimal.valueOf(200))
                .build(),
            CryptoPrice.builder()
                .symbol("LTC")
                .timestamp(LocalDateTime.now())
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

  @Test
  @DisplayName("Should throw exception when crypto prices list is empty")
  void shouldThrowExceptionWhenCryptoPricesListIsEmpty() {
    List<CryptoPrice> cryptoPrices = List.of();

    assertThatThrownBy(() -> dataNormalizer.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Crypto prices list is empty");
  }

  @Test
  @DisplayName("Should normalize data when there is only one crypto price")
  void shouldNormalizeDataWhenThereIsOnlyOneCryptoPrice() {
    List<CryptoPrice> cryptoPrices =
        Collections.singletonList(
            CryptoPrice.builder()
                .symbol("BTC")
                .timestamp(LocalDateTime.now())
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
    List<CryptoPrice> cryptoPrices =
        Arrays.asList(
            CryptoPrice.builder()
                .symbol("BTC")
                .timestamp(LocalDateTime.now())
                .price(BigDecimal.valueOf(-100))
                .build(),
            CryptoPrice.builder()
                .symbol("ETH")
                .timestamp(LocalDateTime.now())
                .price(BigDecimal.valueOf(-200))
                .build(),
            CryptoPrice.builder()
                .symbol("LTC")
                .timestamp(LocalDateTime.now())
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
}
