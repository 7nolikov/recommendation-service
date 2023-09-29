package com.xm.recommendation.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
class CryptoIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testGetAllCryptosSortedByNormalizedRangeDesc() {
    ResponseEntity<List<CryptoPriceDto>> response = restTemplate.exchange("/crypto/all/sorted-by-normalized-range/desc",
        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<CryptoPriceDto> cryptos = response.getBody();
    assertNotNull(cryptos);
    assertEquals(2, cryptos.size());
    assertEquals("Bitcoin", cryptos.get(0).getSymbol());
    assertEquals("Ethereum", cryptos.get(1).getSymbol());
  }

  @Test
  void testGetExtremes() {
    Long cryptoSymbol = 1L;
    ResponseEntity<ExtremesDto> response = restTemplate.exchange("/crypto/extremes/{crypto_symbol}", HttpMethod.GET,
        null, ExtremesDto.class, cryptoSymbol);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    ExtremesDto extremesDto = response.getBody();
    assertNotNull(extremesDto);
    assertNotNull(extremesDto.getOldestPrice());
    assertNotNull(extremesDto.getNewestPrice());
    assertNotNull(extremesDto.getMinPrice());
    assertNotNull(extremesDto.getMaxPrice());
  }

  @Test
  void testGetCryptoWithHighestNormalizedRange() {
    String day = "2023-09-27";
    ResponseEntity<CryptoPriceDto> response = restTemplate.exchange("/crypto/highest-normalized-range/{day}",
        HttpMethod.GET, null, CryptoPriceDto.class, day);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    CryptoPriceDto crypto = response.getBody();
    assertNotNull(crypto);
    assertNotNull(crypto.getSymbol());
    assertNotNull(crypto.getTimestamp());
    assertNotNull(crypto.getPrice());
  }
}
