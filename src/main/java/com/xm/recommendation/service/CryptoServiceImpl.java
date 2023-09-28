package com.xm.recommendation.service;

import com.xm.recommendation.config.ConfigProperties;
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
  private final ConfigProperties properties;

  @PostConstruct
  private void postConstruct() {
    List<CryptoPrice> cryptoPrices = cryptoPriceLoader.load();
    log.info("Crypto prices loaded total: {}", cryptoPrices.size());
  }

  @Override
  public List<CryptoPriceDto> getAllCryptosSortedByNormalizedRange() {
    List<CryptoPrice> cryptos = Collections.emptyList();
    return cryptos.stream().map(crypto -> CryptoPriceDto.builder().build()).collect(Collectors.toList());
  }

  @Override
  public List<ExtremesDto> getExtremes(String cryptoSymbol) {
    log.info(properties.getPricesPath());
    return null;
  }

  @Override
  public CryptoPriceDto findWithHighestNormalizedRange(String day) {
    return null;
  }
}
