package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

/**
 * DTO for crypto price.
 */
@Builder
public record CryptoPrice(LocalDateTime timestamp, String symbol, BigDecimal price) {}
