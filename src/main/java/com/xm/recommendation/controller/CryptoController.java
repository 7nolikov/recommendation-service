package com.xm.recommendation.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import com.xm.recommendation.service.CryptoService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for getting cryptocurrencies information. */
@RestController
@RequestMapping("crypto")
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
  public ResponseEntity<ExtremesDto> getExtremes(
      @PathVariable("crypto_symbol") String cryptoSymbol) {
    return cryptoService
        .getExtremes(cryptoSymbol)
        .map(ResponseEntity::ok)
        .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
  }

  /**
   * Returns the crypto with the highest normalized range for a specific day.
   *
   * @param day the day to get the highest normalized range for
   * @return the crypto with the highest normalized range for the given day
   */
  @GetMapping("/highest-normalized-range")
  public ResponseEntity<CryptoPriceDto> findWithHighestNormalizedRange(
      @RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
    return cryptoService
        .findWithHighestNormalizedRange(day)
        .map(ResponseEntity::ok)
        .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
  }
}
