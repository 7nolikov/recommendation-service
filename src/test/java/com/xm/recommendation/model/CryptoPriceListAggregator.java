package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

/** Aggregator for crypto price list. */
public class CryptoPriceListAggregator implements ArgumentsAggregator {

  @Override
  public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext)
      throws ArgumentsAggregationException {
    List<CryptoPrice> prices = new ArrayList<>();

    for (int i = 0; i < argumentsAccessor.size(); i++) {
      String entry = argumentsAccessor.getString(i);
      String[] parts = entry.trim().split(" ");
      if (parts.length == 1 && parts[0].equals("null")) {
        prices.add(null);
      } else if (parts.length == 3) { // Ensure that each entry has exactly three parts: symbol, price, timestamp
        CryptoPrice price = CryptoPrice.builder()
            .symbol(parts[0])
            .price(new BigDecimal(parts[1]))
            .timestamp(OffsetDateTime.parse(parts[2], DateTimeFormatter.ISO_OFFSET_DATE_TIME))
            .build();
        prices.add(price);
      } else {
        throw new ArgumentsAggregationException("Invalid format for CryptoPrice");
      }
    }

    return prices;
  }
}
