package com.xm.recommendationservice.service;

import com.opencsv.CSVReader;
import com.xm.recommendationservice.config.ConfigurationProperties;
import com.xm.recommendationservice.exception.ResourceCannotBeLoadedException;
import com.xm.recommendationservice.model.CryptoPrice;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * A service that loads crypto prices from CSV files.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CsvCryptoPriceLoader implements CryptoPriceLoader {

  private final ConfigurationProperties properties;

  @Override
  public List<CryptoPrice> loadCryptoPrices() {
    String directoryPath = properties.getPricesSourcePath();
    log.debug("Starting to load data from filepath: {}", directoryPath);

    URL systemResource = ClassLoader.getSystemResource("prices/BTC_values");
//    List<String[]> strings = loadFile(systemResource);
    return Collections.emptyList();
  }

  private List<String[]> loadFile(URL filepath) {
    try (Reader reader = Files.newBufferedReader(Path.of(filepath.toURI()) )) {
      try (CSVReader csvReader = new CSVReader(reader)) {
        return csvReader.readAll();
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ResourceCannotBeLoadedException();
    }
  }
}
