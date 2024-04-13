package com.xm.recommendation.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.params.aggregator.AggregateWith;

/** Annotation for aggregating CSV to crypto price list. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@AggregateWith(CryptoPriceListAggregator.class)
public @interface CsvToCryptoPriceList {
}