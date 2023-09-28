package com.xm.recommendation.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.xm.recommendation.config.ConfigurationProperties;
import com.xm.recommendation.exception.ResourceNotLoadedException;
import com.xm.recommendation.model.CryptoPrice;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
  public List<CryptoPrice> load() {
    String directoryPath = properties.getPricesPath();
    if (directoryPath == null) {
      throw new ResourceNotLoadedException("Source directory was not specified");
    }

    log.debug("Starting to load data from directory: {}", directoryPath);

    List<CryptoPrice> loadedCryptoPrices = new ArrayList<>();
    File directory = new File(directoryPath);
    File[] files = directory.listFiles();

    if (files == null) {
      throw new ResourceNotLoadedException("Specified directory is unavailable");
    }

    for (File file : files) {
      loadedCryptoPrices.addAll(loadFile(file));
    }

    log.debug("Finished loading data");
    return loadedCryptoPrices;
  }

  private List<CryptoPrice> loadFile(File file) {
    log.debug("Starting to load data from file: {}", file.getName());
    if (!file.getName().endsWith("_values.csv")) {
      throw new ResourceNotLoadedException("Source file has incorrect naming pattern" + file.getName());
    }
    try {
      try (CSVReader reader = new CSVReader(new FileReader(file))) {
        List<String[]> rows = reader.readAll();
        return rows.stream().map(row -> CryptoPrice.builder().timestamp(LocalDateTime.parse(row[0])).symbol(row[1])
            .price(new BigDecimal(row[2])).build()).toList();
      }
    } catch (IOException | CsvException e) {
      throw new ResourceNotLoadedException("Resource can't be loaded", e);
    }
  }
}
