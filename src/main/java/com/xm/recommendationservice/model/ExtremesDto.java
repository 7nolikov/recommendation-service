package com.xm.recommendationservice.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ExtremesDto {

  private String symbol;
  private BigDecimal oldestPrice;
  private BigDecimal newestPrice;
  private BigDecimal minPrice;
  private BigDecimal maxPrice;
}
