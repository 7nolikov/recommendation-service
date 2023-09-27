package com.xm.recommendationservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.xm.recommendationservice.model.CryptoPriceDto;
import com.xm.recommendationservice.model.ExtremesDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CryptoServiceImplTest {

  @InjectMocks
  CryptoServiceImpl service;

  @Test
  void getAllCryptosSortedByNormalizedRangeDesc() {
    List<CryptoPriceDto> allCryptosSortedByNormalizedRangeDesc = service.getAllCryptosSortedByNormalizedRangeDesc();
    List<CryptoPriceDto> expected = Collections.emptyList();
    assertEquals(expected, allCryptosSortedByNormalizedRangeDesc);
  }

  @Test
  void getExtremes() {
    List<ExtremesDto> extremes = service.getExtremes(any());
    List<ExtremesDto> expected = Collections.emptyList();
    assertEquals(expected, extremes);
  }

  @Test
  void getCryptoWithHighestNormalizedRange() {
    CryptoPriceDto cryptoWithHighestNormalizedRange = service.getCryptoWithHighestNormalizedRange(any());
    CryptoPriceDto expected = CryptoPriceDto.builder().build();
    assertEquals(expected, cryptoWithHighestNormalizedRange);
  }
}