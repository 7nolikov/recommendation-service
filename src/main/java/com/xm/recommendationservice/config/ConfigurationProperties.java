package com.xm.recommendationservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@PropertySource("classpath:application.properties")
@Data
public class ConfigurationProperties {

  private String pricesSourcePath;
}
