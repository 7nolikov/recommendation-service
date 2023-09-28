package com.xm.recommendationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.xm.recommendationservice.config.ConfigurationProperties;
import com.xm.recommendationservice.exception.ResourceNotLoadedException;
import com.xm.recommendationservice.model.CryptoPrice;
import java.io.File;
import java.io.FileReader;
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
  private static final String INCORRECT_FILE_PATH = "/path/to/directory/BTC_values.txt";

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
    when(properties.getPricesSourcePath()).thenReturn(DIRECTORY_PATH);
    when(file.listFiles()).thenReturn(new File[]{new File(FILE_PATH)});

    List<CryptoPrice> cryptoPrices = csvCryptoPriceLoader.loadCryptoPrices();

    assertEquals(1, cryptoPrices.size());
    CryptoPrice cryptoPrice = cryptoPrices.get(0);
    assertEquals(LocalDateTime.parse("2023-09-28T16:26:31.000Z"), cryptoPrice.getTimestamp());
    assertEquals("BTC", cryptoPrice.getSymbol());
    assertEquals(new BigDecimal("100000"), cryptoPrice.getPrice());
  }

  @Test
  void shouldThrowExceptionWhenDirectoryIsUnavailable() {
    when(properties.getPricesSourcePath()).thenReturn(null);

    assertThrows(ResourceNotLoadedException.class, () -> csvCryptoPriceLoader.loadCryptoPrices());
  }

  @Test
  void shouldThrowExceptionWhenFileHasIncorrectNamingPattern() {
    when(properties.getPricesSourcePath()).thenReturn(DIRECTORY_PATH);

    assertThrows(ResourceNotLoadedException.class, () -> csvCryptoPriceLoader.loadCryptoPrices());
  }

  @Test
  void shouldThrowExceptionWhenFileCannotBeLoaded() throws IOException, CsvException {
    when(properties.getPricesSourcePath()).thenReturn(DIRECTORY_PATH);
    when(properties.getPricesSourcePath()).thenReturn(DIRECTORY_PATH);

    assertThrows(ResourceNotLoadedException.class, () -> csvCryptoPriceLoader.loadCryptoPrices());
  }
}