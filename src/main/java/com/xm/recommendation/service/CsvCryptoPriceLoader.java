package com.xm.recommendation.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.xm.recommendation.config.ConfigProperties;
import com.xm.recommendation.exception.ResourceNotLoadedException;
import com.xm.recommendation.model.CryptoPrice;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

/**
 * A service that loads crypto prices from CSV files.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CsvCryptoPriceLoader implements CryptoPriceLoader {

  private final ConfigProperties properties;

  @Override
  public List<CryptoPrice> load() {
    String directoryPath = properties.getPricesPath();
    if (directoryPath == null) {
      throw new ResourceNotLoadedException("Source directory was not specified");
    }

    log.info("Starting to load data from directory: {}", directoryPath);
    List<CryptoPrice> loadedCryptoPrices = new ArrayList<>();
    try {
      PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
      Resource[] resources = resolver.getResources(directoryPath + "/*.csv");

      for (Resource resource : resources) {
        File file = resource.getFile();
        loadedCryptoPrices.addAll(loadFile(file));
      }
    } catch (IOException e) {
      throw new ResourceNotLoadedException("Failed to load resources from directory: " + directoryPath, e);
    }

    log.info("Finished loading data. Crypto prices loaded total: {}", loadedCryptoPrices.size());
    return loadedCryptoPrices;
  }

  /**
   * Load data from a file.
   *
   * @param file the file to load data from
   * @return a list of crypto prices
   */
  private List<CryptoPrice> loadFile(File file) {
    log.debug("Starting to load data from file: {}", file.getName());
    if (!file.getName().endsWith("_values.csv")) {
      throw new ResourceNotLoadedException("Source file has incorrect naming pattern" + file.getName());
    }
    try {
      try (CSVReader reader = new CSVReader(new FileReader(file))) {
        List<String[]> rows = reader.readAll();
        rows.remove(0);
        return rows.stream().map(row -> CryptoPrice.builder()
            .timestamp(LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(row[0])), ZoneOffset.UTC))
            .symbol(row[1]).price(new BigDecimal(row[2])).build()).toList();
      }
    } catch (IOException | CsvException e) {
      throw new ResourceNotLoadedException("Resource can't be loaded", e);
    }
  }
}
