package io.fusionauth.config;

import java.util.List;
import java.util.stream.Collectors;

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
import static java.util.Collections.emptyList;

/**
 * @author Tyler Scott
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/resources/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests(authorizeRequests ->
                               authorizeRequests.mvcMatchers("/", "/login", "/register", "/error").permitAll()
                                                .antMatchers("/css/**").permitAll()

                                                .anyRequest().authenticated())
        .oauth2Login(oauth2 ->
                         oauth2.userInfoEndpoint(userInfo ->
                                                     userInfo.userAuthoritiesMapper(userAuthoritiesMapper())));
  }

  private GrantedAuthoritiesMapper userAuthoritiesMapper() {
    return authorities -> authorities.stream()
                                     .flatMap(authority -> {
                                       List<String> roles;
                                       if (authority instanceof OidcUserAuthority) {
                                         roles = (List<String>) ((OidcUserAuthority) authority).getIdToken().getClaims().getOrDefault("roles", emptyList());
                                       } else if (authority instanceof OAuth2UserAuthority) {
                                         roles = (List<String>) ((OAuth2UserAuthority) authority).getAttributes().getOrDefault("roles", emptyList());
                                       } else {
                                         roles = emptyList();
                                       }

                                       return roles.stream();
                                     })
                                     .map(SimpleGrantedAuthority::new)
                                     .collect(Collectors.toList());
  }
}
