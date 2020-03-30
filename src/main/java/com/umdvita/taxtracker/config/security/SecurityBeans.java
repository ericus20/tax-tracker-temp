package com.umdvita.taxtracker.config.security;

import com.umdvita.taxtracker.config.core.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;
import java.security.SecureRandom;

import static com.umdvita.taxtracker.constant.security.SecurityConfigConstant.STRENGTH;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The SecurityBeans class defines beans needed for the security operation of the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class SecurityBeans {

  private final DataSource dataSource;
  private final ApplicationProperties properties;

  @Autowired
  public SecurityBeans(DataSource dataSource, ApplicationProperties properties) {
    this.dataSource = dataSource;
    this.properties = properties;
  }

  @Bean
  public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
    return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
  }

  /**
   * The password encoder bean used to encrypt and decrypt data to and from the database.
   *
   * @return the BCryptPasswordEncoder
   */
  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(STRENGTH, new SecureRandom(properties.getSalt().getBytes(UTF_8)));
  }

  /**
   * Providing a default implementation fo the session registry.
   *
   * @return sessionRegistry
   */
  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  /**
   * Making use of persistent option instead of in-memory for maximum security.
   *
   * @return persistentTokenRepository
   */
  @Bean
  public PersistentTokenRepository persistentRepository() {
    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    jdbcTokenRepository.setDataSource(dataSource);
    return jdbcTokenRepository;
  }

  /**
   * Determines if a slash "/" that is URL encoded "%2F" should be allowed in the path
   * or not. The default is to not allow this behavior because it is a common way to
   * bypass URL based security.
   * should a slash "/" that is URL encoded "%2F" be allowed in the path or not. Default is false.
   */
  @Bean
  public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
    StrictHttpFirewall firewall = new StrictHttpFirewall();
    firewall.setAllowUrlEncodedSlash(true);
    return firewall;
  }
}
