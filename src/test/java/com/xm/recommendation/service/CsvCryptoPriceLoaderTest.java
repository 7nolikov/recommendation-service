package com.xm.recommendation.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

import com.xm.recommendation.config.ConfigProperties;
import com.xm.recommendation.exception.ResourceNotLoadedException;
import com.xm.recommendation.model.CryptoPrice;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CsvCryptoPriceLoaderTest {

  @InjectMocks private CsvCryptoPriceLoader csvCryptoPriceLoader;
  @Mock private ConfigProperties properties;

  @BeforeEach
  void setUp() {
    String relativePath = Paths.get("testData").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);
  }

  @ParameterizedTest
  @DisplayName("Should load crypto prices from CSV file")
  @CsvSource({
    "0, 1641009600000, BTC, 46813.21",
    "1, 1641020400000, BTC, 46979.61",
    "2, 1641031200000, BTC, 47143.98",
    "3, 1641034800000, BTC, 46871.09",
    "4, 1641045600000, BTC, 47023.24",
  })
  void shouldLoadCryptoPrices(
      String numberStr, String timestampStr, String symbol, String priceStr) {
    int number = Integer.parseInt(numberStr);
    long timestamp = Long.parseLong(timestampStr);
    BigDecimal price = new BigDecimal(priceStr);
    LocalDateTime dateTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);

    List<CryptoPrice> cryptoPrices = csvCryptoPriceLoader.load();

    assertThat(cryptoPrices).isNotEmpty();
    assertThat(cryptoPrices).hasSize(5);

    CryptoPrice cryptoPrice = cryptoPrices.get(number);
    assertThat(cryptoPrice.symbol()).isEqualTo(symbol.trim());
    assertThat(cryptoPrice.price()).isEqualByComparingTo(price);
    assertThat(cryptoPrice.timestamp()).isEqualTo(dateTime);
  }

  @Test
  @DisplayName("Should throw exception when prices path is null")
  void shouldThrowExceptionWhenPricesPathIsNull() {
    when(properties.getPricesPath()).thenReturn(null);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file is empty")
  void shouldThrowExceptionWhenCsvFileIsEmpty() {
    String relativePath = Paths.get("testDataEmptyFile").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file has incorrect data format")
  void shouldThrowExceptionWhenCsvFileHasIncorrectDataFormat() {
    String relativePath = Paths.get("testDataIncorrectFormat").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file has missing fields")
  void shouldThrowExceptionWhenCsvFileHasMissingFields() {
    String relativePath = Paths.get("testDataMissingFields").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file has extra fields")
  void shouldThrowExceptionWhenCsvFileHasExtraFields() {
    String relativePath = Paths.get("testDataExtraFields").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file has invalid timestamp")
  void shouldThrowExceptionWhenCsvFileHasInvalidTimestamp() {
    String relativePath = Paths.get("testDataInvalidTimestamp").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file has invalid symbol")
  void shouldThrowExceptionWhenCsvFileHasInvalidSymbol() {
    String relativePath = Paths.get("testDataInvalidSymbol").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file has invalid price")
  void shouldThrowExceptionWhenCsvFileHasInvalidPrice() {
    String relativePath = Paths.get("testDataInvalidPrice").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file has non-numeric price")
  void shouldThrowExceptionWhenCsvFileHasNonNumericPrice() {
    String relativePath = Paths.get("testDataNonNumericPrice").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file has non-numeric timestamp")
  void shouldThrowExceptionWhenCsvFileHasNonNumericTimestamp() {
    String relativePath = Paths.get("testDataNonNumericTimestamp").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }

  @Test
  @DisplayName("Should throw exception when CSV file has negative price")
  void shouldThrowExceptionWhenCsvFileHasNegativePrice() {
    String relativePath = Paths.get("testDataNegativePrice").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);

    assertThatThrownBy(() -> csvCryptoPriceLoader.load())
        .isInstanceOf(ResourceNotLoadedException.class);
  }
}
