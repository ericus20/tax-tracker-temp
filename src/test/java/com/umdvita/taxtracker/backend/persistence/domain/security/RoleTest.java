package com.umdvita.taxtracker.backend.persistence.domain.security;

import com.umdvita.taxtracker.TestUtility;
import com.umdvita.taxtracker.enums.RoleType;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class RoleTest {

  @Test
  void equalsContract(TestInfo testInfo) {

    Role role = new Role(RoleType.VOLUNTEER);
    Role role2 = new Role(RoleType.USER);

    EqualsVerifier.forClass(Role.class)
            .withPrefabValues(Role.class, role, role2)
            .withRedefinedSuperclass()
            .withIgnoredFields(TestUtility.ENTITY_GLOBAL_FIELDS_TO_IGNORE)
            .verify();
  }

}