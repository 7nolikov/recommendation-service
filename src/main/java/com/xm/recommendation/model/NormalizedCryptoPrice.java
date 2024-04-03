package com.xm.recommendation.model;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;

/**
 * A normalized crypto price.
 */
@Builder
public record NormalizedCryptoPrice(
    String symbol,
    BigDecimal price,
    LocalDateTime timestamp,
    BigDecimal normalizedPrice) implements Comparable<NormalizedCryptoPrice> {

  @Override
  @NotNull
  public int compareTo(@NotNull NormalizedCryptoPrice other) {
    Objects.requireNonNull(other, "NormalizedCryptoPrice cannot be compared to null");
    return normalizedPrice.compareTo(other.normalizedPrice);
  }
}
