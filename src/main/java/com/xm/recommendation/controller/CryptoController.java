package com.xm.recommendation.controller;

import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import com.xm.recommendation.service.CryptoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for getting cryptocurrencies information.
 */
@RestController
@RequestMapping("v1/crypto")
@RequiredArgsConstructor
public class CryptoController {

  private final CryptoService cryptoService;

  /**
   * Returns a descending sorted list of all the cryptos, comparing the normalized range.
   *
   * @return a descending sorted list of all the cryptos, comparing the normalized range
   */
  @GetMapping("/sorted-by-normalized-range")
  public List<CryptoPriceDto> getAllCryptosSortedByNormalizedRange() {
    return cryptoService.getAllCryptosSortedByNormalizedRange();
  }

  /**
   * Returns the oldest/newest/min/max values for a requested crypto.
   *
   * @param cryptoSymbol the ID of the crypto to get the oldest/newest/min/max values for
   * @return the oldest/newest/min/max values for the given crypto
   */
  @GetMapping(value = "/extremes/{crypto_symbol}", produces = MediaType.APPLICATION_JSON_VALUE)
  ExtremesDto getExtremes(@PathVariable("crypto_symbol") String cryptoSymbol) {
    return cryptoService.getExtremes(cryptoSymbol);
  }

  /**
   * Returns the crypto with the highest normalized range for a specific day.
   *
   * @param day the day to get the highest normalized range for
   * @return the crypto with the highest normalized range for the given day
   */
  @GetMapping("/highest-normalized-range/{day}")
  public CryptoPriceDto findWithHighestNormalizedRange(@PathVariable("day") String day) {
    return cryptoService.findWithHighestNormalizedRange(day);
  }
}
