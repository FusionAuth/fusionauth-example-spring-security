package io.fusionauth.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

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

  @RequestMapping("/logout")
  @PreAuthorize("permitAll()")
  public RedirectView logout(@Autowired HttpServletRequest request) throws ServletException {
    request.logout();
    return new RedirectView("/");
  }

  @RequestMapping("/profile")
  @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
  public String profile() {
    return "profile";
  }
}
