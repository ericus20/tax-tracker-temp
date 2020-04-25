package com.umdvita.taxtracker.backend.persistence.domain.security.user;

import com.umdvita.taxtracker.TestUtility;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class UserHistoryTest {

  @Test
  void equalsContract(TestInfo testInfo) {
    UserDto userDto = UserUtility.createTestUser(testInfo.getDisplayName(), true, false);
    UserDto userDto2 = UserUtility.createTestUser(testInfo.getDisplayName(), true, false);
    userDto.setEmail(TestUtility.TEST_EMAIL);

    EqualsVerifier.forClass(UserHistory.class)
            .withPrefabValues(User.class, UserUtility.getUserFromDto(userDto), UserUtility.getUserFromDto(userDto2))
            .withRedefinedSuperclass()
            .withIgnoredFields(TestUtility.ENTITY_GLOBAL_FIELDS_TO_IGNORE)
            .verify();
  }

}