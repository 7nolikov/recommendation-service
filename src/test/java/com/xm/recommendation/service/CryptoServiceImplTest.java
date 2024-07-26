package com.xm.recommendation.service;

import static java.util.Comparator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import com.xm.recommendation.model.NormalizedCryptoPrice;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Crypto service tests")
class CryptoServiceImplTest {

  @InjectMocks private CryptoServiceImpl cryptoService;

  @Mock private CryptoPriceLoader cryptoPriceLoader;
  @Mock private DataNormalizerImpl dataNormalizerImpl;
  @Mock private ExtremesCalculator extremesCalculator;

  private List<CryptoPrice> cryptoPrices;
  private Map<String, ExtremesDto> extremes;
  private List<NormalizedCryptoPrice> normalizedCryptoPrices;

  @BeforeEach
  void setUp() {
    cryptoPrices =
        List.of(
            CryptoPrice.builder()
                .symbol("BTC")
                .price(BigDecimal.ONE)
                .timestamp(OffsetDateTime.now())
                .build(),
            CryptoPrice.builder()
                .symbol("ETH")
                .price(BigDecimal.TEN)
                .timestamp(OffsetDateTime.now())
                .build());
    extremes =
        Map.of(
            "BTC", ExtremesDto.builder().minPrice(BigDecimal.ONE).maxPrice(BigDecimal.TEN).build(),
            "ETH", ExtremesDto.builder().minPrice(BigDecimal.ONE).maxPrice(BigDecimal.TEN).build());
    normalizedCryptoPrices =
        List.of(
            NormalizedCryptoPrice.builder()
                .symbol("BTC")
                .price(BigDecimal.ONE)
                .timestamp(OffsetDateTime.now())
                .normalizedPrice(BigDecimal.ZERO)
                .build(),
            NormalizedCryptoPrice.builder()
                .symbol("ETH")
                .price(BigDecimal.TEN)
                .timestamp(OffsetDateTime.now())
                .normalizedPrice(BigDecimal.ONE)
                .build());
  }

  @Test
  @DisplayName("Should return all cryptos sorted by normalized range")
  void shouldReturnAllCryptosSortedByNormalizedRange() {
    // Given
    when(dataNormalizerImpl.normalize(any(), any())).thenReturn(normalizedCryptoPrices);

    // When
    List<CryptoPriceDto> result = cryptoService.getAllCryptosSortedByNormalizedRange();

    // Then
    assertThat(result).hasSize(2);
    CryptoPriceDto firstDto = result.get(0);
    CryptoPriceDto secondDto = result.get(1);
    assertThat(firstDto.symbol()).isEqualTo("ETH");
    assertThat(firstDto.price()).isEqualTo(BigDecimal.TEN);
    assertThat(firstDto.normalizedPrice()).isEqualTo(BigDecimal.ONE);
    assertThat(secondDto.symbol()).isEqualTo("BTC");
    assertThat(secondDto.price()).isEqualTo(BigDecimal.ONE);
    assertThat(secondDto.normalizedPrice()).isEqualTo(BigDecimal.ZERO);
    verify(dataNormalizerImpl).normalize(any(), any());
    assertThat(result).isSortedAccordingTo(comparing(CryptoPriceDto::normalizedPrice, reverseOrder()));
  }

  @Test
  @DisplayName("Should return empty list when no cryptos available")
  void shouldReturnEmptyWhenNoCryptosAvailable() {
    // Given
    when(dataNormalizerImpl.normalize(any(), any())).thenReturn(Collections.emptyList());

    // When
    List<CryptoPriceDto> result = cryptoService.getAllCryptosSortedByNormalizedRange();

    // Then
    assertThat(result).isEmpty();
    verify(dataNormalizerImpl).normalize(any(), any());
  }

  @Test
  @DisplayName("Should handle exception when normalizing cryptos")
  void shouldHandleExceptionWhenNormalizingCryptos() {
    // Given
    when(dataNormalizerImpl.normalize(any(), any()))
        .thenThrow(new RuntimeException("Test exception"));

    // When
    assertThatThrownBy(() -> cryptoService.getAllCryptosSortedByNormalizedRange())
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Test exception");
  }

  @Test
  @DisplayName("Should return list with single dto when single cryptoPrice is available")
  void shouldReturnSingleDtoWhenSingleCryptoPrice() {
    // Given
    when(dataNormalizerImpl.normalize(any(), any()))
        .thenReturn(
            Collections.singletonList(
                NormalizedCryptoPrice.builder()
                    .symbol("BTC")
                    .price(BigDecimal.ONE)
                    .timestamp(OffsetDateTime.now())
                    .normalizedPrice(BigDecimal.ZERO)
                    .build()));

    // When
    List<CryptoPriceDto> result = cryptoService.getAllCryptosSortedByNormalizedRange();

    // Then
    assertThat(result).hasSize(1);
    CryptoPriceDto dto = result.get(0);
    assertThat(dto.symbol()).isEqualTo("BTC");
    assertThat(dto.price()).isEqualTo(BigDecimal.ONE);
    assertThat(dto.normalizedPrice()).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  @DisplayName("Should return empty when getting extremes for non-existing symbol")
  void shouldReturnEmptyWhenGettingExtremesForNonExistingSymbol() {
    // Given
    String nonExistingSymbol = "DOGE";

    // Then
    assertThatThrownBy(() -> cryptoService.getExtremes(nonExistingSymbol))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("Should return empty when finding highest normalized range for non-existing day")
  void shouldReturnEmptyWhenFindingHighestNormalizedRangeForNonExistingDay() {
    // Given
    LocalDate nonExistingDay = LocalDate.parse("2023-01-01");

    // When
    Optional<CryptoPriceDto> result = cryptoService.findWithHighestNormalizedRange(nonExistingDay);

    // Then
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("Should handle when normalizing cryptos returns null")
  void shouldHandleWhenNormalizingCryptosReturnsNull() {
    // Given
    when(dataNormalizerImpl.normalize(any(), any())).thenReturn(null);

    // Then
    assertThatThrownBy(() -> cryptoService.getAllCryptosSortedByNormalizedRange())
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("Should return crypto with highest normalized range for existing day")
  void shouldReturnCryptoWithHighestNormalizedRangeForExistingDay() {
    // Given
    LocalDate existingDay = LocalDate.now();
    NormalizedCryptoPrice highestNormalizedPrice = NormalizedCryptoPrice.builder()
        .symbol("BTC")
        .price(BigDecimal.ONE)
        .timestamp(OffsetDateTime.now())
        .normalizedPrice(BigDecimal.ONE)
        .build();
    when(dataNormalizerImpl.normalize(any(), any())).thenReturn(List.of(highestNormalizedPrice));

    // When
    Optional<CryptoPriceDto> result = cryptoService.findWithHighestNormalizedRange(existingDay);

    // Then
    assertThat(result).isNotEmpty();
    assertThat(result.get().symbol()).isEqualTo(highestNormalizedPrice.symbol());
    assertThat(result.get().price()).isEqualTo(highestNormalizedPrice.price());
    assertThat(result.get().normalizedPrice()).isEqualTo(highestNormalizedPrice.normalizedPrice());
  }
}
