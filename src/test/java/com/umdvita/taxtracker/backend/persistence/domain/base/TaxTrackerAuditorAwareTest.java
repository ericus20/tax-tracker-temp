package com.umdvita.taxtracker.backend.persistence.domain.base;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This tests the TaxTrackerAuditorAware of the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
class TaxTrackerAuditorAwareTest {

  private static final String SYSTEM_USER = "system";
  private static final String ROLE_USER = "ROLE_USER";
  private static final String ANONYMOUS_USER = "anonymousUser";
  private static final String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";

  @BeforeEach
  void setUp() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void getCurrentAuditor() {
    Assertions.assertEquals(SYSTEM_USER, getAuditor());
  }

  @Test
  void getCurrentAuditorWithNoAuthentication() {
    setAuthentication(ANONYMOUS_ROLE, ANONYMOUS_USER);
    SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    Assertions.assertEquals(SYSTEM_USER, getAuditor());
  }

  @Test
  void getCurrentAuditorWithAnonymousUser() {
    setAuthentication(ANONYMOUS_ROLE, ANONYMOUS_USER);
    Assertions.assertEquals(SYSTEM_USER, getAuditor());
  }

  @Test
  void getCurrentAuditorWithAuthenticatedUser(TestInfo testInfo) {
    setAuthentication(ROLE_USER, testInfo.getDisplayName());
    Assertions.assertEquals(getAuditor(), testInfo.getDisplayName());
  }

  private String getAuditor() {
    TaxTrackerAuditorAware interviewWizardAuditorAware = new TaxTrackerAuditorAware();
    Optional<String> currentAuditor = interviewWizardAuditorAware.getCurrentAuditor();
    return currentAuditor.orElse(null);
  }

  @Test
  void equalsContract() {
    EqualsVerifier.forClass(TaxTrackerAuditorAware.class)
            .verify();
  }

  private void setAuthentication(String role, String user) {
    List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }
}