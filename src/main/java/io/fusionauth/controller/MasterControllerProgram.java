package io.fusionauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Tyler Scott
 */
@Controller
public class MasterControllerProgram {
  @GetMapping(value = "/admin")
  @PreAuthorize("hasAuthority('admin')")
  public String viewAdmin() {
    return "admin";
  }

  @GetMapping(value = "/")
  public String viewHome() {
    return "hello";
  }

  @GetMapping(value = "/profile")
  @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
  public String viewProfile() {
    return "profile";
  }

  @GetMapping("/403")
  public String getAccessDenied() {
    return "403";
  }
}
