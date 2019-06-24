package io.fusionauth.controller;

import java.util.Map;

import io.fusionauth.util.MapBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Tyler Scott
 */
@ControllerAdvice
public class ExceptionHandlers {
  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView handleRegistrationError(Throwable t) {
    Map<String, Object> data = new MapBuilder<String, Object>()
        .put("status", 400)
        .put("error", t.getMessage())
        .build();

    return new ModelAndView("error", data);
  }
}
