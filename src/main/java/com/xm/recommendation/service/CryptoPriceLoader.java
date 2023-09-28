package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
import java.util.List;

/**
 * A service that loads crypto prices.
 */
public interface CryptoPriceLoader {

  /**
   * Loads a list of crypto prices.
   *
   * @return A list of crypto prices, or empty list if no prices could be loaded.
   */
  List<CryptoPrice> load();
}
