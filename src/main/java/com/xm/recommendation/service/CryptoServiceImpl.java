package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
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
    return cryptoPrices.stream().map(crypto -> CryptoPriceDto.builder().build()).toList();
  }

  @Override
  public Optional<ExtremesDto> getExtremes(String cryptoSymbol) {
    return Optional.empty();
  }

  @Override
  public Optional<CryptoPriceDto> findWithHighestNormalizedRange(String day) {
    return Optional.empty();
  }
}
