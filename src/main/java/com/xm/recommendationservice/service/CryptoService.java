package com.xm.recommendationservice.service;

import com.xm.recommendationservice.model.CryptoPriceDto;
import com.xm.recommendationservice.model.ExtremesDto;
import java.util.List;

public interface CryptoService {

  List<CryptoPriceDto> getAllCryptosSortedByNormalizedRangeDesc();

  List<ExtremesDto> getExtremes(String cryptoSymbol);

  CryptoPriceDto getCryptoWithHighestNormalizedRange(String day);
}
