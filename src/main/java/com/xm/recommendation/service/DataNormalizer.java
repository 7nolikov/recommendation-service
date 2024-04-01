package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
import java.util.List;

/**
 * A service that normalizes data.
 */
public interface DataNormalizer {

  /**
   * Normalize data using the specified strategy.
   *
   * @param cryptoPrices the data to normalize
   * @param strategy the normalization strategy
   * @return the normalized data
   */
  List<CryptoPrice> normalize(List<CryptoPrice> cryptoPrices, NormalizationStrategy strategy);
}
