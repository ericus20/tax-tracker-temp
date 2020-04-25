package com.umdvita.taxtracker.backend.persistence.domain.security;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PersistentLoginsTest {

  @Test
  void equalsContract() {
    EqualsVerifier.forClass(PersistentLogins.class)
            .withIgnoredFields("series")
            .verify();
  }

}