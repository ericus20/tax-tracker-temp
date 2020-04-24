package com.umdvita.taxtracker.backend.persistence.domain.base;

import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

/**
 * This class gets the applications current auditor which is the username of the authenticated user.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@EqualsAndHashCode
public final class TaxTrackerAuditorAware implements AuditorAware<String> {

  private static final String CURRENT_AUDITOR = "system";

  /**
   * Returns the current auditor of the application.
   *
   * @return the current auditor
   */
  @NonNull
  @Override
  public Optional<String> getCurrentAuditor() {

    // Check if there is a user logged in.
    // If so, use the logged in user as the current auditor.
    // spring injects an anonymousUser if there is no
    // authentication and authorization
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (Objects.nonNull(authentication)
            && authentication.isAuthenticated()
            && authentication.getPrincipal() != "anonymousUser") {
      return Optional.ofNullable(authentication.getName());
    }

    // If there is no authentication,
    // then the system will be used as the current auditor.
    return Optional.of(CURRENT_AUDITOR);
  }
}
