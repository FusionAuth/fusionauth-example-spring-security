package io.fusionauth.config;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

/**
 * @author Tyler Scott
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Value("${fusionAuth.redirectUri}")
  private String redirectUri;

  @Value("${fusionAuth.clientId}")
  private String cliendId;

  @Value("${fusionAuth.logoutUri}")
  private String logoutUri;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/resources/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/error", "/login**").permitAll()
        .anyRequest().permitAll()
        .and()
        .oauth2Login()
        .userInfoEndpoint()
        .userAuthoritiesMapper(userAuthoritiesMapper())
        .and()
        .defaultSuccessUrl("/profile");

    if (!logoutUri.isEmpty()) {
      http.logout()
          .logoutSuccessUrl(logoutUri + "?client_id=" + cliendId); // If you set the logoutUri, people will be redirected here after its complete.
      // In this case, we also append the client_id because if we redirect to FusionAuth this way, it can redirect us back after it logs us
      // out globally.
    }
  }

  private GrantedAuthoritiesMapper userAuthoritiesMapper() {
    return authorities -> authorities.stream()
                                     .flatMap(authority -> {
                                       List<String> roles;
                                       if (authority instanceof OidcUserAuthority) {
                                         roles = (List<String>) ((OidcUserAuthority) authority).getIdToken().getClaims().getOrDefault("roles", Collections.emptyList());
                                       } else if (authority instanceof OAuth2UserAuthority) {
                                         roles = (List<String>) ((OAuth2UserAuthority) authority).getAttributes().getOrDefault("roles", Collections.emptyList());
                                       } else {
                                         roles = Collections.emptyList();
                                       }

                                       return roles.stream();
                                     })
                                     .map(SimpleGrantedAuthority::new)
                                     .collect(Collectors.toList());
  }
}
