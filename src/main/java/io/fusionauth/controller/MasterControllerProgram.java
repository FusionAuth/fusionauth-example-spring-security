package io.fusionauth.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Tyler Scott
 */
@Controller
public class MasterControllerProgram {
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  @PreAuthorize("permitAll()")
  public RedirectView handleLogout(@Autowired HttpServletRequest request, @RequestParam String redirect) throws ServletException {
    request.logout();
    if (redirect != null && redirect.length() != 0) {
      return new RedirectView(redirect);
    }
    return new RedirectView("/");
  }

  @RequestMapping(value = "/admin", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('admin')")
  public String viewAdmin() {
    return "admin";
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  @PreAuthorize("permitAll()")
  public String viewHome() {
    return "hello";
  }

  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
  public String viewProfile() {
    return "profile";
  }
}
