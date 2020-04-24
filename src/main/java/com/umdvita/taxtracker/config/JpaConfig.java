package com.umdvita.taxtracker.config;

import com.umdvita.taxtracker.backend.persistence.domain.base.TaxTrackerAuditorAware;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration targeting the JPA and transactions of database.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableCaching
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(basePackages = "com.umdvita.taxtracker.backend.persistence.repository")
@EntityScan(basePackages = "com.umdvita.taxtracker.backend.persistence.domain")
public class JpaConfig {

  /**
   * A bean to be served for the AuditorAware interface.
   *
   * @return auditorAware instance.
   */
  @Bean
  public AuditorAware<String> auditorAware() {
    return new TaxTrackerAuditorAware();
  }
}
