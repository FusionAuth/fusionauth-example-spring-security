package io.fusionauth.config;

import io.fusionauth.security.FusionAuthUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Tyler Scott
 */
@ControllerAdvice
public class GlobalUser {
  @ModelAttribute("user")
  public Object getUser() {
    Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (user instanceof FusionAuthUserDetails) {
      return user;
    }
    return null;
  }
}
