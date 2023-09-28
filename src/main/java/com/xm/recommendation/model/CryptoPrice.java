package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoPrice {

  private LocalDateTime timestamp;
  private String symbol;
  private BigDecimal price;
}
