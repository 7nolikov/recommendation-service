package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.ExtremesDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Implementation of the ExtremesCalculator interface. */
@Service
@Slf4j
public class ExtremesCalculatorImpl implements ExtremesCalculator {

  @Override
  public Map<String, ExtremesDto> calculateExtremes(List<CryptoPrice> cryptoPrices) {
    log.debug("Calculating extremes for data source with {} crypto prices", cryptoPrices.size());
    Map<String, ExtremesDto> extremesMap = new HashMap<>();

    for (CryptoPrice cryptoPrice : cryptoPrices) {
      ExtremesDto extreme = extremesMap.get(cryptoPrice.symbol());

      if (extreme == null) {
        extremesMap.put(cryptoPrice.symbol(), ExtremesDto.fromCryptoPrice(cryptoPrice));
        continue;
      }
      if (cryptoPrice.price().compareTo(extreme.minPrice()) < 0) {
        extremesMap.put(
            cryptoPrice.symbol(),
            ExtremesDto.fromMinPrice(extremesMap.get(cryptoPrice.symbol()), cryptoPrice.price()));
      }
      if (cryptoPrice.price().compareTo(extreme.maxPrice()) > 0) {
        extremesMap.put(
            cryptoPrice.symbol(),
            ExtremesDto.fromMaxPrice(extremesMap.get(cryptoPrice.symbol()), cryptoPrice.price()));
      }
      if (cryptoPrice.timestamp().isBefore(extreme.oldestTimestamp())) {
        extremesMap.put(
            cryptoPrice.symbol(),
            ExtremesDto.fromMinTimestamp(
                extremesMap.get(cryptoPrice.symbol()),
                cryptoPrice.price(),
                cryptoPrice.timestamp()));
      }
      if (cryptoPrice.timestamp().isAfter(extreme.newestTimestamp())) {
        extremesMap.put(
            cryptoPrice.symbol(),
            ExtremesDto.fromMaxTimestamp(
                extremesMap.get(cryptoPrice.symbol()),
                cryptoPrice.price(),
                cryptoPrice.timestamp()));
      }
    }

    log.info("Extremes calculated: {}", extremesMap.size());
    return extremesMap;
  }
}
