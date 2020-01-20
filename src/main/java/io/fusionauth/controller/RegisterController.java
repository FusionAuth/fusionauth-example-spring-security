package io.fusionauth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.UUID;

import com.inversoft.error.Errors;
import com.inversoft.rest.ClientResponse;
import io.fusionauth.client.FusionAuthClient;
import io.fusionauth.domain.User;
import io.fusionauth.domain.UserRegistration;
import io.fusionauth.domain.api.user.RegistrationRequest;
import io.fusionauth.domain.api.user.RegistrationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Tyler Scott
 */
@Controller
public class RegisterController {
  private final FusionAuthClient fusionAuthClient;

  @Value("${fusionAuth.applicationId}")
  private String appId;

  public RegisterController(FusionAuthClient fusionAuthClient) {
    this.fusionAuthClient = fusionAuthClient;
  }

  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String handleRegister(@Valid Registration registration,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Model model) {

    if (!Objects.equals(registration.password, registration.confirmPassword)) {
      bindingResult.addError(new ObjectError("confirmPassword", "The passwords must match"));
    }

    if (bindingResult.hasErrors()) {
      model.addAttribute("registration", registration);
      model.addAttribute("bindingResult", bindingResult);
      return "register";
    }

    UserRegistration userRegistration = new UserRegistration()
        .with(reg -> reg.applicationId = UUID.fromString(appId))
        .with(reg -> reg.roles.add("user"));

    User newUser = new User()
        .with(user -> user.email = registration.email)
        .with(user -> user.password = registration.password);

    ClientResponse<RegistrationResponse, Errors> response = fusionAuthClient.register(null, new RegistrationRequest(newUser, userRegistration));

    if (response.wasSuccessful()) {
      // Force the user to logout and then login again (we didn't log them out of fusionauth so it should be seamless to them)
      // We do this because we need to refresh their roles.
      // We also use the programmatic logout because the redirect logout will detect a missing csrf and ask the user to logout.
      // This is confusing so we force it here.
      new SecurityContextLogoutHandler().logout(request, null, null);
      return "redirect:/oauth2/login/fusionauth";
    } else {
      throw new RegistrationException(response.errorResponse.toString());
    }
  }

  @GetMapping("/register")
  public String viewRegister(OAuth2AuthenticationToken authentication, Model model) {

    if (authentication != null && authentication.isAuthenticated()) { // User is logged in (register immediately)
      UserRegistration registration = new UserRegistration()
          .with(reg -> reg.userId = UUID.fromString(authentication.getName()))
          .with(reg -> reg.applicationId = UUID.fromString(appId))
          .with(reg -> reg.roles.add("user"));

      ClientResponse<RegistrationResponse, Errors> response = fusionAuthClient.register(registration.userId, new RegistrationRequest(null, registration));

      if (response.wasSuccessful()) {
        return "redirect:/";
      } else {
        throw new RegistrationException(response.errorResponse.toString());
      }
    }

    model.addAttribute("registration", new Registration());

    return "register";
  }

  @Validated
  public static class Registration {
    @NotBlank
    public String confirmPassword;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String password;

    public String getConfirmPassword() {
      return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  public static class RegistrationException extends ResponseStatusException {
    RegistrationException(String cause) {
      super(HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
  }
}
