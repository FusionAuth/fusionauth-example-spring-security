package io.fusionauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Tyler Scott
 */
@ControllerAdvice
public class GlobalTemplateVariables {
  @Value("${fusionAuth.clientId}")
  public String clientid;

  @Value("${fusionAuth.logoutUri}")
  public String logoutUri;

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
    return isLoggedIn() && !SecurityContextHolder.getContext().getAuthentication().getAuthorities().isEmpty();
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
