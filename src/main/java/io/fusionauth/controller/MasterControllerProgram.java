package io.fusionauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tyler Scott
 */
@Controller
public class MasterControllerProgram {
  @RequestMapping("/admin")
  @PreAuthorize("hasAuthority('admin')")
  public String admin() {
    return "admin";
  }

  @RequestMapping("/")
  @PreAuthorize("permitAll()")
  public String home() {
    return "hello";
  }

  @RequestMapping("/profile")
  @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
  public String profile() {
    return "profile";
  }
}
