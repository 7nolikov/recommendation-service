package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for managing cryptocurrencies.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoServiceImpl implements CryptoService {

  private final CryptoPriceLoader cryptoPriceLoader;
  private List<CryptoPrice> cryptoPrices;

  @PostConstruct
  private void postConstruct() {
    cryptoPrices = cryptoPriceLoader.load();
    log.info("Crypto prices loaded total: {}", cryptoPrices.size());
  }

  @Override
  public List<CryptoPriceDto> getAllCryptosSortedByNormalizedRange() {
    List<CryptoPrice> cryptos = Collections.emptyList();
    return cryptos.stream().map(crypto -> CryptoPriceDto.builder().build()).toList();
  }

  @Override
  public ExtremesDto getExtremes(String cryptoSymbol) {
    List<CryptoPrice> specificCrypto = cryptoPrices.stream().filter(crypto -> crypto.getSymbol().equals(cryptoSymbol))
        .collect(Collectors.toList());
    return ExtremesDto.builder()
        .symbol(cryptoSymbol)
        .oldestPrice(null)
        .newestPrice(null)
        .minPrice(null)
        .maxPrice(null)
        .build();
  }

  @Override
  public CryptoPriceDto findWithHighestNormalizedRange(String day) {
    return null;
  }
}
