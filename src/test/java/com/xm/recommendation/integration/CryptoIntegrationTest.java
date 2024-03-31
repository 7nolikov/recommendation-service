package com.xm.recommendation.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xm.recommendation.model.CryptoPriceDto;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoIntegrationTest {

  private static final String SORTED_BY_NORMALIZED_RANGE_URL = "/crypto/sorted-by-normalized-range";
  private static final String EXTREMES_URL = "/crypto/extremes/{crypto_symbol}";
  private static final String HIGHEST_NORMALIZED_RANGE_URL = "/crypto/highest-normalized-range/{day}";
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void shouldReturnAllCryptosSortedByNormalizedRange() {
    ResponseEntity<CryptoPriceDto[]> response = restTemplate.getForEntity(SORTED_BY_NORMALIZED_RANGE_URL, CryptoPriceDto[].class);
    List<CryptoPriceDto> cryptoPriceDtos = Arrays.asList(Objects.requireNonNull(response.getBody()));

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(cryptoPriceDtos);
    assertFalse(cryptoPriceDtos.isEmpty());
  }
}
