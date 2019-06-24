package io.fusionauth.config;

import io.fusionauth.client.FusionAuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tyler Scott
 */
@Configuration
public class FusionAuthClientConfig {

  @Value("${fusionAuth.apiKey}")
  private String apiKey;

  @Value("${fusionAuth.baseUrl}")
  private String baseUrl;

  @Bean
  public FusionAuthClient setupClient() {
    return new FusionAuthClient(apiKey, baseUrl);
  }
}
