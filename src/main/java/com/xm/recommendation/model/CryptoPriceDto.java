package com.xm.recommendation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CryptoPriceDto(LocalDateTime timestamp, String symbol, BigDecimal price) {}
