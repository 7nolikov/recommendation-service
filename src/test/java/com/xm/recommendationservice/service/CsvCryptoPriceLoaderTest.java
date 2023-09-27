package com.xm.recommendationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.xm.recommendationservice.config.ConfigurationProperties;
import com.xm.recommendationservice.model.CryptoPrice;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CsvCryptoPriceLoaderTest {

  private static final String TEST_DIRECTORY_PATH = "prices";
  @InjectMocks
  CsvCryptoPriceLoader loader;

  @Mock
  ConfigurationProperties properties;

  @Test
  void testLoadCryptoPrices() {
    when(properties.getPricesSourcePath()).thenReturn(TEST_DIRECTORY_PATH);
    List<CryptoPrice> cryptoPrices = loader.loadCryptoPrices();

    List<CryptoPrice> expected = Collections.EMPTY_LIST;
    assertEquals(expected, cryptoPrices);
  }
}