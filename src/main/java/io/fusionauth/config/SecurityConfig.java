package io.fusionauth.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

/**
 * @author Tyler Scott
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${fusionAuth.redirectUri}")
  private String redirectUri;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/resources/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/error").permitAll()
        .anyRequest().permitAll()
        .and()
        .oauth2Login()
        .loginPage("/login")
        .redirectionEndpoint().baseUri(redirectUri)
        .and()
        .userInfoEndpoint()
        .userAuthoritiesMapper(userAuthoritiesMapper());
  }

  @Bean
  protected GrantedAuthoritiesMapper userAuthoritiesMapper() {
    return authorities -> {
      authorities.forEach(authority -> System.out.println(authority.toString()));

      return Collections.emptyList(); // FIXME
    };
  }
}
