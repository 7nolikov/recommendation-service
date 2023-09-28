package com.xm.recommendation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.xm.recommendation.config.ConfigurationProperties;
import com.xm.recommendation.exception.ResourceNotLoadedException;
import com.xm.recommendation.model.CryptoPrice;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CsvCryptoPriceLoaderTest {

  private static final String DIRECTORY_PATH = "/path/to/directory";
  private static final String FILE_PATH = "/path/to/directory/BTC_values.csv";

  @InjectMocks
  private CsvCryptoPriceLoader csvCryptoPriceLoader;
  @Mock
  private ConfigurationProperties properties;
  @Mock
  private File file;
  @Mock
  private CSVReader csvReader;

  @Test
  void shouldLoadCryptoPrices() {
    when(properties.getPricesPath()).thenReturn(DIRECTORY_PATH);
    when(file.listFiles()).thenReturn(new File[]{new File(FILE_PATH)});

    List<CryptoPrice> cryptoPrices = csvCryptoPriceLoader.load();

    assertEquals(1, cryptoPrices.size());
    CryptoPrice cryptoPrice = cryptoPrices.get(0);
    assertEquals(LocalDateTime.parse("2023-09-28T16:26:31.000Z"), cryptoPrice.getTimestamp());
    assertEquals("BTC", cryptoPrice.getSymbol());
    assertEquals(new BigDecimal("100000"), cryptoPrice.getPrice());
  }

  @Test
  void shouldThrowExceptionWhenDirectoryIsUnavailable() {
    when(properties.getPricesPath()).thenReturn(null);

    assertThrows(ResourceNotLoadedException.class, () -> csvCryptoPriceLoader.load());
  }

  @Test
  void shouldThrowExceptionWhenFileHasIncorrectNamingPattern() {
    when(properties.getPricesPath()).thenReturn(DIRECTORY_PATH);

    assertThrows(ResourceNotLoadedException.class, () -> csvCryptoPriceLoader.load());
  }

  @Test
  void shouldThrowExceptionWhenFileCannotBeLoaded() throws IOException, CsvException {
    when(properties.getPricesPath()).thenReturn(DIRECTORY_PATH);
    when(properties.getPricesPath()).thenReturn(DIRECTORY_PATH);

    assertThrows(ResourceNotLoadedException.class, () -> csvCryptoPriceLoader.load());
  }
}