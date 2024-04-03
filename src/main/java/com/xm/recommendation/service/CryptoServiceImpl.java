package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import com.xm.recommendation.model.NormalizedCryptoPrice;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Service for managing cryptocurrencies. */
@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoServiceImpl implements CryptoService {

  private final CryptoPriceLoader cryptoPriceLoader;
  private final DataNormalizerImpl dataNormalizerImpl;
  private List<CryptoPrice> cryptoPrices;

  /** Load the crypto prices after the bean is constructed. */
  @PostConstruct
  private void postConstruct() {
    cryptoPrices = cryptoPriceLoader.load();
    log.info("Crypto prices loaded total: {}", cryptoPrices.size());
  }

  @Override
  public List<CryptoPriceDto> getAllCryptosSortedByNormalizedRange() {
    List<NormalizedCryptoPrice> normalizedAndSortedPrices =
        dataNormalizerImpl.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX).stream()
            .sorted()
            .toList();
    return CryptoPriceDto.fromNormalizedCryptoPrices(normalizedAndSortedPrices);
  }

  @Override
  public Optional<ExtremesDto> getExtremes(String cryptoSymbol) {
    return Optional.of(ExtremesDto.builder().symbol("BTC").build());
  }

  @Override
  public Optional<CryptoPriceDto> findWithHighestNormalizedRange(String day) {
    return Optional.of(CryptoPriceDto.builder().symbol("BTC").build());
  }
}
