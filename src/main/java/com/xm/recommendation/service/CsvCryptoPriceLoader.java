package com.xm.recommendation.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.xm.recommendation.config.ConfigProperties;
import com.xm.recommendation.exception.ResourceNotLoadedException;
import com.xm.recommendation.model.CryptoPrice;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

/** A service that loads crypto prices from CSV files. */
@Service
@RequiredArgsConstructor
@Slf4j
public class CsvCryptoPriceLoader implements CryptoPriceLoader {

  private static final String CSV_FILE_MASK = "/*.csv";
  private static final String SOURCE_FILE_NAMING_POSTFIX_PATTERN = "_values.csv";

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
      Resource[] resources = resolver.getResources("%s%s".formatted(directoryPath, CSV_FILE_MASK));
      log.debug("Found {} resources in the directory", resources.length);

      for (Resource resource : resources) {
        loadedCryptoPrices.addAll(loadFile(resource));
      }
    } catch (IOException e) {
      throw new ResourceNotLoadedException(
          "Failed to load resources from directory: " + directoryPath, e);
    }

    log.info("Finished loading data. Crypto prices loaded total: {}", loadedCryptoPrices.size());
    return loadedCryptoPrices;
  }

  /**
   * Load data from a file.
   *
   * @param resource the resource to load data from
   * @return a list of crypto prices
   */
  private List<CryptoPrice> loadFile(Resource resource) {
    String fileName = resource.getFilename();
    log.debug("Starting to load data from file: {}", fileName);
    if (fileName == null || !fileName.endsWith(SOURCE_FILE_NAMING_POSTFIX_PATTERN)) {
      throw new ResourceNotLoadedException(
          "Source file has incorrect naming pattern" + fileName);
    }
    try {
      try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
        List<String[]> rows = reader.readAll();
        rows.remove(0);

        List<CryptoPrice> cryptoPrices =
            rows.stream()
                .map(
                    row ->
                        CryptoPrice.builder()
                            .timestamp(
                                OffsetDateTime.ofInstant(
                                    Instant.ofEpochMilli(Long.parseLong(row[0])), ZoneOffset.UTC))
                            .symbol(row[1])
                            .price(new BigDecimal(row[2]))
                            .build())
                .toList();

        log.debug(
            "Finished loading data from file: {}. Loaded {} values.",
            fileName,
            cryptoPrices.size());
        return cryptoPrices;
      }
    } catch (IOException | CsvException e) {
      throw new ResourceNotLoadedException("Resource can't be loaded", e);
    }
  }
}
