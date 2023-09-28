package com.xm.recommendation.service;

import com.xm.recommendation.cache.CryptoCache;
import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import jakarta.annotation.PostConstruct;
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

  private final CryptoCache cryptoCache;
  private final CryptoPriceLoader cryptoPriceLoader;

  @PostConstruct
  private void postConstruct() {
    List<CryptoPrice> cryptoPrices = cryptoPriceLoader.load();
    log.info("Crypto prices loaded total: {}", cryptoPrices.size());
    cryptoCache.set("all", cryptoPrices);
  }

  @Override
  public List<CryptoPriceDto> getAllCryptosSortedByNormalizedRange() {
    List<CryptoPrice> cryptos = cryptoCache.get("all");
    return cryptos.stream().map(crypto -> CryptoPriceDto.builder().build()).collect(Collectors.toList());
  }

  @Override
  public List<ExtremesDto> getExtremes(String cryptoSymbol) {
    return null;
  }

  @Override
  public CryptoPriceDto findWithHighestNormalizedRange(String day) {
    return null;
  }
}
