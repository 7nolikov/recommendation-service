package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.ExtremesDto;
import java.util.List;
import java.util.Map;

/** Interface for calculating the extremes of a given symbol. */
public interface ExtremesCalculator {
  
  /**
   * Calculate the extremes for a given symbol.
   *
   * @param symbol the symbol
   * @return the extremes
   */
  Map<String, ExtremesDto> calculateExtremes(List<CryptoPrice> symbol);
}
