package com.xm.recommendation.config;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@Data
public class ConfigurationProperties {

  private String pricesPath;
}
