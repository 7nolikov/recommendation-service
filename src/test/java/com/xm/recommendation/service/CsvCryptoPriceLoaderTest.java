package com.xm.recommendation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.xm.recommendation.config.ConfigProperties;
import com.xm.recommendation.model.CryptoPrice;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@ExtendWith(MockitoExtension.class)
class CsvCryptoPriceLoaderTest {

  @InjectMocks
  private CsvCryptoPriceLoader csvCryptoPriceLoader;
  @Mock
  private ConfigProperties properties;

  @BeforeEach
  void setUp() {
    URL resourceUrl = getClass().getResource("/testData");
    assertNotNull(resourceUrl, "Test data directory must exist");

    String testDirectoryPath = resourceUrl.getPath();
    when(properties.getPricesPath()).thenReturn(testDirectoryPath);
  }

  @Test
  void shouldLoadCryptoPrices() {
    List<CryptoPrice> cryptoPrices = csvCryptoPriceLoader.load();

    assertFalse(cryptoPrices.isEmpty(), "Expected non-empty list of crypto prices");
    assertEquals(5, cryptoPrices.size());
    CryptoPrice cryptoPrice = cryptoPrices.get(0);
    assertEquals("BTC", cryptoPrice.getSymbol());
    assertEquals(new BigDecimal("46813.21"), cryptoPrice.getPrice());
    assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(1641009600000L), ZoneOffset.UTC),
        cryptoPrice.getTimestamp());
  }
}