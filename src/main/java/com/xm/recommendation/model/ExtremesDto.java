package com.xm.recommendation.model;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ExtremesDto(
    String symbol,
    BigDecimal oldestPrice,
    BigDecimal newestPrice,
    BigDecimal minPrice,
    BigDecimal maxPrice) {}
