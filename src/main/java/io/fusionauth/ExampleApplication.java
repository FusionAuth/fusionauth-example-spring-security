package io.fusionauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Tyler Scott
 */
@SpringBootApplication
public class ExampleApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(ExampleApplication.class, args);
  }
}
