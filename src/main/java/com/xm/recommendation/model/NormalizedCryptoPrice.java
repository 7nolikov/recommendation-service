package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record NormalizedCryptoPrice(
    String symbol,
    BigDecimal price,
    LocalDateTime timestamp,
    BigDecimal normalizedPrice) {}
