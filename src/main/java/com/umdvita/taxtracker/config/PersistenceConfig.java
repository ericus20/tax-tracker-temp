package com.umdvita.taxtracker.config;

import com.umdvita.taxtracker.backend.persistence.domain.base.TaxTrackerAuditorAware;
import com.umdvita.taxtracker.constant.ProfileType;
import io.hypersistence.optimizer.HypersistenceOptimizer;
import io.hypersistence.optimizer.core.config.JpaConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

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
public class PersistenceConfig {

  /**
   * A bean to be served for the AuditorAware interface.
   *
   * @return auditorAware instance.
   */
  @Bean
  public AuditorAware<String> auditorAware() {
    return new TaxTrackerAuditorAware();
  }

  /**
   * Hypersistence Optimizer uses a mapping, a configuration, and a runtime scanner.
   * The mapping and configuration scanners will trigger events when you create a HypersistenceOptimizer instance.
   *
   * @param entityManagerFactory the entityManagerFactory
   * @see EntityManagerFactory
   * @return hypersistenceOptimizer {@link HypersistenceOptimizer @HypersistenceOptimizer}
   */
  @Bean
  @Profile(ProfileType.PROD)
  public HypersistenceOptimizer hypersistenceOptimizer(EntityManagerFactory entityManagerFactory) {
    return new HypersistenceOptimizer(new JpaConfig(entityManagerFactory));
  }

}
