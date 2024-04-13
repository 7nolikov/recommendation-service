package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

/** Aggregator for crypto price. */
public class CryptoPriceAggregator implements ArgumentsAggregator {

  @Override
  public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext)
      throws ArgumentsAggregationException {
    return CryptoPrice.builder()
        .symbol(argumentsAccessor.get(0, String.class))
        .price(argumentsAccessor.get(1, BigDecimal.class))
        .timestamp(argumentsAccessor.get(0, LocalDateTime.class))
        .build();
  }
}
