package com.xm.recommendation.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoIntegrationTest {

  private static final String SORTED_BY_NORMALIZED_RANGE_URL = "/crypto/sorted-by-normalized-range";
  private static final String EXTREMES_URL = "/crypto/extremes/%s";
  private static final String HIGHEST_NORMALIZED_RANGE_URL = "/crypto/highest-normalized-range/%s";
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName("Should return all cryptos sorted by normalized range - Not empty list")
  void shouldReturnAllCryptosSortedByNormalizedRange() {
    ResponseEntity<CryptoPriceDto[]> response = restTemplate.getForEntity(SORTED_BY_NORMALIZED_RANGE_URL, CryptoPriceDto[].class);
    List<CryptoPriceDto> cryptoPriceDtos = Arrays.asList(Objects.requireNonNull(response.getBody()));

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(cryptoPriceDtos);
    assertFalse(cryptoPriceDtos.isEmpty());
  }

  @Test
  @DisplayName("Should return all cryptos sorted by normalized range - List contains crypto with symbol, timestamp, price fields")
  void shouldReturnAllCryptosSortedByNormalizedRangeContainsSymbolTimestampPrice() {
    ResponseEntity<CryptoPriceDto[]> response = restTemplate.getForEntity(SORTED_BY_NORMALIZED_RANGE_URL, CryptoPriceDto[].class);
    List<CryptoPriceDto> cryptoPriceDtos = Arrays.asList(Objects.requireNonNull(response.getBody()));

    assertTrue(
        cryptoPriceDtos.stream()
            .allMatch(
                cryptoPriceDto ->
                    cryptoPriceDto.symbol() != null
                        && cryptoPriceDto.timestamp() != null
                        && cryptoPriceDto.price() != null));
  }

  @Test
  void shouldReturnCryptoPriceWhenCryptoSymbolExistsForExtremes() {
    String existentCryptoSymbol = "BTC";
    ResponseEntity<ExtremesDto> response = restTemplate.getForEntity(EXTREMES_URL.formatted(existentCryptoSymbol), ExtremesDto.class, existentCryptoSymbol);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void shouldReturnCryptoPriceWhenCryptoSymbolExistsForHighestNormalizedRange() {
    String existentCryptoSymbol = "BTC";
    ResponseEntity<CryptoPriceDto> response = restTemplate.getForEntity(HIGHEST_NORMALIZED_RANGE_URL, CryptoPriceDto.class, existentCryptoSymbol);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void shouldReturnCryptoPriceWhenDateIsValidForHighestNormalizedRange() {
    String validDate = "2022-01-01";
    ResponseEntity<CryptoPriceDto> response = restTemplate.getForEntity(HIGHEST_NORMALIZED_RANGE_URL, CryptoPriceDto.class, validDate);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }
}
