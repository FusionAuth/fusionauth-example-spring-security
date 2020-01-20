package io.fusionauth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * @author Tyler Scott
 */
@EnableWebMvc
@Configuration
@ControllerAdvice
public class WebConfig implements WebMvcConfigurer {
  @Value("${fusionAuth.clientId}")
  public String clientid;

  @Value("${fusionAuth.logoutUri}")
  public String logoutUri;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/*.css")
            .addResourceLocations("/resources/static/css/", "classpath:/static/css/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
  }

  @Bean
  public FreeMarkerConfigurer freeMarkerConfig() {
    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates/");
    return freeMarkerConfigurer;
  }

  @Bean
  public FreeMarkerViewResolver freeMarkerViewResolver() {
    FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
    viewResolver.setCache(true);
    viewResolver.setSuffix(".ftl");
    return viewResolver;
  }

  @ModelAttribute("logoutUri")
  public String getLogoutUri() {
    return logoutUri + "?client_id=" + clientid;
  }

  @ModelAttribute("user")
  public Object getUser() {
    try {
      return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    } catch (NullPointerException ignore) {
      return null;
    }
  }

  @ModelAttribute("hasRoles")
  public boolean hasRoles() {
    if (!isLoggedIn()) {
      return false;
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof OAuth2AuthenticationToken && authentication.getPrincipal() instanceof OidcUser) {
      List<String> roles = ((OidcUser) authentication.getPrincipal()).getClaimAsStringList("roles");
      return roles != null && !roles.isEmpty();
    }

    return false; // Unknown principal type
  }

  @ModelAttribute("loggedIn")
  public boolean isLoggedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null &&
        authentication.isAuthenticated() &&
        //when Anonymous Authentication is enabled
        !(authentication instanceof AnonymousAuthenticationToken);
  }
}
