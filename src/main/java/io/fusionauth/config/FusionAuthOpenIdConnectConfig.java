package io.fusionauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

/**
 * @author Tyler Scott
 */
@Configuration
public class FusionAuthOpenIdConnectConfig {
  @Value("${fusionAuth.accessTokenUri}")
  private String accessTokenUri;

  @Value("${fusionAuth.clientId}")
  private String clientId;

  @Value("${fusionAuth.clientSecret}")
  private String clientSecret;

  @Value("${fusionAuth.jwksUri}")
  private String jwksUri;

  @Value("${fusionAuth.redirectUri}")
  private String redirectUri;

  @Value("${fusionAuth.userAuthorizationUri}")
  private String userAuthorizationUri;

  @Value("${fusionAuth.userInfoUri}")
  private String userInfoUri;

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(fusionAuthClientRegistration());
  }

  private ClientRegistration fusionAuthClientRegistration() {
    return ClientRegistration.withRegistrationId("fusionAuth")
                             .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                             .authorizationUri(userAuthorizationUri)
                             .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                             .clientId(clientId)
                             .clientName("fusionAuth")
                             .clientSecret(clientSecret)
                             .jwkSetUri(jwksUri)
                             .redirectUriTemplate(redirectUri)
                             .scope("openid", "email")
                             .tokenUri(accessTokenUri)
                             .userInfoUri(userInfoUri)
                             .userNameAttributeName(IdTokenClaimNames.SUB)
                             .build();
  }
}
