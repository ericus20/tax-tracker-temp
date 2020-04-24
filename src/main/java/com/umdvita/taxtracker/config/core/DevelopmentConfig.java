package com.umdvita.taxtracker.config.core;

import com.umdvita.taxtracker.constant.ProfileType;
import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * The DevelopmentConfig class handles all the configurations needed for development environment.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Configuration
@Profile(value = {ProfileType.DEV, ProfileType.TEST})
public class DevelopmentConfig {

  public static final String H2_CONSOLE_URL_MAPPING = "/console/*";

  /**
   * This bean is being used to register the path /console for h2 database.
   *
   * @return the servletRegistrationBean.
   */
  @Bean
  public ServletRegistrationBean<WebServlet> h2ConsoleServletRegistration() {
    return new ServletRegistrationBean<>(new WebServlet(), H2_CONSOLE_URL_MAPPING);
  }
}
