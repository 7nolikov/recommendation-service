package com.xm.recommendationservice.service;

import com.xm.recommendationservice.cache.CryptoCache;
import com.xm.recommendationservice.model.CryptoPriceDto;
import com.xm.recommendationservice.model.ExtremesDto;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for managing cryptocurrencies.
 */
@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

  private final CryptoCache cryptoCache;
  private final CryptoPriceLoader cryptoPriceLoader;

  @PostConstruct
  private void postConstruct() {

  }

  @Override
  public List<CryptoPriceDto> getAllCryptosSortedByNormalizedRangeDesc() {
    return null;
  }

  @Override
  public List<ExtremesDto> getExtremes(String cryptoSymbol) {
    return null;
  }

  @Override
  public CryptoPriceDto getCryptoWithHighestNormalizedRange(String day) {
    return null;
  }
}
