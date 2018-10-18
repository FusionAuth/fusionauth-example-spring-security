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
  public String admin() {
    return "admin";
  }

  @RequestMapping("/")
  public String home() {
    return "hello";
  }

  @RequestMapping("/profile")
  public String profile() {
    return "profile";
  }
}
