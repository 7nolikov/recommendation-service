package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import java.util.List;

/**
 * Service for managing cryptocurrencies.
 */
public interface CryptoService {

  /**
   * Returns a descending sorted list of all the cryptos, comparing the normalized range.
   *
   * @return a descending sorted list of all the cryptos, comparing the normalized range
   */
  List<CryptoPriceDto> getAllCryptosSortedByNormalizedRange();

  /**
   * Returns the oldest/newest/min/max values for a requested crypto.
   *
   * @param cryptoSymbol the ID of the crypto to get the oldest/newest/min/max values for
   * @return the oldest/newest/min/max values for the given crypto
   */
  ExtremesDto getExtremes(String cryptoSymbol);

  /**
   * Returns the crypto with the highest normalized range for a specific day.
   *
   * @param day the day to get the highest normalized range for
   * @return the crypto with the highest normalized range for the given day
   */
  CryptoPriceDto findWithHighestNormalizedRange(String day);
}
