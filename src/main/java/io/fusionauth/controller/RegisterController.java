package io.fusionauth.controller;

import java.util.UUID;

import com.inversoft.rest.ClientResponse;
import io.fusionauth.client.FusionAuthClient;
import io.fusionauth.domain.User;
import io.fusionauth.domain.UserRegistration;
import io.fusionauth.domain.api.user.RegistrationRequest;
import io.fusionauth.security.FusionAuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Tyler Scott
 */
@Controller()
public class RegisterController {
  @Value("${fusionAuth.applicationId}")
  private String appId;

  @Autowired
  private FusionAuthClient fusionAuthClient;

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  @PreAuthorize("permitAll()")
  public String registerGet() {
    return "register";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @PreAuthorize("permitAll()")
  public View registerPost(@RequestBody MultiValueMap<String, String> body) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    ClientResponse response;

    if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof FusionAuthUserDetails) { // User is logged in
      FusionAuthUserDetails userDetails = (FusionAuthUserDetails) authentication.getPrincipal();
      UserRegistration registration = new UserRegistration()
          .with(reg -> reg.userId = UUID.fromString(userDetails.userId))
          .with(reg -> reg.applicationId = UUID.fromString(appId))
          .with(reg -> reg.roles.add("user"));

      response = fusionAuthClient.register(registration.userId, new RegistrationRequest(null, registration));
    } else { // This is a new user
      UserRegistration registration = new UserRegistration()
          .with(reg -> reg.applicationId = UUID.fromString(appId))
          .with(reg -> reg.roles.add("user"));

      User newUser = new User()
          .with(user -> user.username = body.getFirst("username"))
          .with(user -> user.password = body.getFirst("password"));

      response = fusionAuthClient.register(null, new RegistrationRequest(newUser, registration));
    }

    if (response.wasSuccessful()) {
      return new RedirectView("/");
    } else {
      throw new RegistrationException(response.errorResponse.toString());
    }
  }

  public class RegistrationException extends RuntimeException {
    RegistrationException(String cause) {
      super(cause);
    }
  }
}