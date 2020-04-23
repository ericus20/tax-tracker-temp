package com.umdvita.taxtracker.config.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * The WebMvcConfig class provides general configurations related to the web layer.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  public static final String[] PATHS = {
          "/uploads/**",
          "/tax/uploads/**",
          "/tax/status/uploads/**",
          "/tax/start/uploads/**",
          "/appointment/uploads/**",
          "/tax/documents/uploads/**",
          "/admin/appointment/uploads/**",
          "/leader/appointment/uploads/**",
  };

  /**
   * Registers a path to allow access to uploads where resources can be fetched to be viewed in browser.
   *
   * @param registry the resourceHandlerRegistry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
            .addResourceHandler(PATHS)
            .addResourceLocations("file:resources/", "file:uploads/")
            .setCachePeriod(0)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
  }
}
