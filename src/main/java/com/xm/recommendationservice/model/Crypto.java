package com.xm.recommendationservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Crypto {

  private LocalDateTime timestamp;
  private String symbol;
  private BigDecimal price;
}
