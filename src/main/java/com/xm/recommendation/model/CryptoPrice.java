package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;

/**
 * DTO for crypto price.
 */
@Builder
public record CryptoPrice(String symbol, BigDecimal price, OffsetDateTime timestamp) {}
