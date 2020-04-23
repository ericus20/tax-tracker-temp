package com.umdvita.taxtracker.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Defines the Internationalization configuration.
 *
 * @author Eric Opoku
 * @since 1.0
 * @version 1.0
 */
@Configuration
public class I18NConfig implements WebMvcConfigurer {

  /**
   * The number of seconds for the message source to reload.
   */
  private static final int CACHE_MILLIS = 1800;
  private static final String MESSAGES_LOCATION = "classpath:i18n/messages";

  /**
   * Setup message source.
   *
   * @return messageSource.
   */
  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename(MESSAGES_LOCATION);
    messageSource.setCacheMillis(CACHE_MILLIS);
    messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
    return messageSource;
  }

  /**
   * Setup localeResolver. The LocaleResolver interface has implementations
   * that determine the current locale based on the session, cookies, the
   * Accept-Language header, or a fixed value.
   *
   * @return localResolver.
   */
  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver localeResolver = new CookieLocaleResolver();
    localeResolver.setDefaultLocale(Locale.US);
    return localeResolver;
  }
}
