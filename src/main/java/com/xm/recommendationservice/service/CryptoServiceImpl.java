package com.xm.recommendationservice.service;

import com.xm.recommendationservice.model.CryptoPriceDto;
import com.xm.recommendationservice.model.ExtremesDto;
import java.util.List;

public class CryptoServiceImpl implements CryptoService {

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
