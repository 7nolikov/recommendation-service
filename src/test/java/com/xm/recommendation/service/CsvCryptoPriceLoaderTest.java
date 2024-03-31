package com.xm.recommendation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import com.xm.recommendation.config.ConfigProperties;
import com.xm.recommendation.model.CryptoPrice;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CsvCryptoPriceLoaderTest {

  @InjectMocks
  private CsvCryptoPriceLoader csvCryptoPriceLoader;
  @Mock
  private ConfigProperties properties;

  @BeforeEach
  void setUp() {
    String relativePath = Paths.get("testData").toString();
    when(properties.getPricesPath()).thenReturn(relativePath);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 1641009600000, BTC, 46813.21",
      "1, 1641020400000, BTC, 46979.61",
      "2, 1641031200000, BTC, 47143.98",
      "3, 1641034800000, BTC, 46871.09",
      "4, 1641045600000, BTC, 47023.24",
  })
  void shouldLoadCryptoPrices(String numberStr, String timestampStr, String symbol, String priceStr) {
    int number = Integer.parseInt(numberStr);
    long timestamp = Long.parseLong(timestampStr);
    BigDecimal price = new BigDecimal(priceStr);
    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);

    List<CryptoPrice> cryptoPrices = csvCryptoPriceLoader.load();

    assertFalse(cryptoPrices.isEmpty(), "Expected non-empty list of crypto prices");
    assertEquals(5, cryptoPrices.size());

    CryptoPrice cryptoPrice = cryptoPrices.get(number);
    assertEquals(symbol.trim(), cryptoPrice.symbol());
    assertEquals(price, cryptoPrice.price());
    assertEquals(dateTime, cryptoPrice.timestamp());
  }
}