package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoPrice;
import com.xm.recommendation.model.CryptoPriceDto;
import com.xm.recommendation.model.ExtremesDto;
import com.xm.recommendation.model.NormalizedCryptoPrice;
import jakarta.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
  private final ExtremesCalculator extremesCalculator;
  private List<CryptoPrice> cryptoPrices;
  private Map<String, ExtremesDto> extremes;

  /** Load the crypto prices after the bean is constructed. */
  @PostConstruct
  private void postConstruct() {
    cryptoPrices = cryptoPriceLoader.load();
    extremes = extremesCalculator.calculateExtremes(cryptoPrices);
  }

  @Override
  public List<CryptoPriceDto> getAllCryptosSortedByNormalizedRange() {
    List<NormalizedCryptoPrice> normalizedAndSortedPrices =
        dataNormalizerImpl.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX).stream()
            .sorted(Comparator.comparing(NormalizedCryptoPrice::normalizedPrice).reversed())
            .toList();
    return CryptoPriceDto.fromNormalizedCryptoPrices(normalizedAndSortedPrices);
  }

  @Override
  public Optional<ExtremesDto> getExtremes(String cryptoSymbol) {
    return Optional.ofNullable(extremes.get(cryptoSymbol));
  }

  @Override
  public Optional<CryptoPriceDto> findWithHighestNormalizedRange(String day) {
    Optional<NormalizedCryptoPrice> highestNormalizedPriceByDay =
        dataNormalizerImpl.normalize(cryptoPrices, NormalizationStrategy.MIN_MAX).stream()
            .filter(
                normalizedCryptoPrice ->
                    normalizedCryptoPrice.timestamp().toLocalDate().toString().equals(day))
            .max(NormalizedCryptoPrice::compareTo);

    return CryptoPriceDto.fromNormalizedCryptoPrices(highestNormalizedPriceByDay);
  }
}
