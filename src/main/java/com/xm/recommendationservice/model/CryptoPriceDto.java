package com.xm.recommendationservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoPriceDto {

  private LocalDateTime timestamp;
  private String symbol;
  private BigDecimal price;
}
