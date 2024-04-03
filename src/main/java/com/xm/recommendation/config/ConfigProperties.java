package com.xm.recommendation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for the application.
 */
@Configuration
@Data
@ConfigurationProperties
public class ConfigProperties {

  /**
   * Path to the directory containing the CSV files with crypto prices.
   */
  private String pricesPath;
}
