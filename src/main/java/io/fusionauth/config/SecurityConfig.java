package io.fusionauth.config;

import io.fusionauth.security.OpenIDConnectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author Tyler Scott
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private OAuth2RestTemplate restTemplate;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/resources/**");
  }

  @Bean
  public OpenIDConnectFilter myFilter() {
    OpenIDConnectFilter filter = new OpenIDConnectFilter("/login");
    filter.setRestTemplate(restTemplate);
    return filter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
        .addFilterAfter(myFilter(), OAuth2ClientContextFilter.class)
        .httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        .and()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/profile").access("hasRole('user') or hasRole('admin')")
        .antMatchers("/admin").access("hasRole('admin')")
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(new OAuth2AccessDeniedHandler())
        .accessDeniedPage("/opps");
  }
}
