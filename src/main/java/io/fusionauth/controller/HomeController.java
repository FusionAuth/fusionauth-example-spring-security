package io.fusionauth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Tyler Scott
 */
@Controller
public class HomeController {
  @RequestMapping("/")
  @ResponseBody
  public String home() {
    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return "Hello " + username;
  }
}
