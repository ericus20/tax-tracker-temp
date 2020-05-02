package com.umdvita.taxtracker.backend.persistence.domain.security;

import com.umdvita.taxtracker.TestUtility;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Unit testing of the password token domain model.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
class PasswordTokenTest {

  private static final String[] FIELDS_TO_IGNORE = {"expiryDate", "user"};

  @Test
  void createTokenWithDefaultExpirationTime() {

    PasswordToken passwordToken = new PasswordToken("token", new User());
    Assertions.assertNotNull(passwordToken);
    Range<Integer> test = Range.between(29, 30);
    long minutes = LocalDateTime
            .now(Clock.systemUTC())
            .until(passwordToken.getExpiryDate(), ChronoUnit.MINUTES);
    Assertions.assertTrue(test.contains((int) minutes));

  }

  @Test
  void createTokenWithCustomExpirationTime() {
    PasswordToken passwordToken = new PasswordToken("token", new User(), 5);
    Assertions.assertNotNull(passwordToken);
    Range<Integer> test = Range.between(4, 5);
    long minutes = LocalDateTime
            .now(Clock.systemUTC())
            .until(passwordToken.getExpiryDate(), ChronoUnit.MINUTES);

    Assertions.assertTrue(test.contains((int) minutes));
  }

  @Test
  void testTokenInvalidPeriodResultingToDefault() {
    UserDto userDto = new UserDto();
    PasswordToken passwordToken = new PasswordToken("fo", UserUtility.getUserFromDto(userDto), 0);
    Assertions.assertTrue(passwordToken.getExpiryDate().isAfter(LocalDateTime.now().plusMinutes(25)));
  }

  @Test
  void equalsContract(TestInfo testInfo) {
    String[] ignoredFields = ArrayUtils.addAll(TestUtility.ENTITY_GLOBAL_FIELDS_TO_IGNORE, FIELDS_TO_IGNORE);
    UserDto userDto = UserUtility.createTestUser(testInfo.getDisplayName(), true, false);
    UserDto userDto2 = UserUtility.createTestUser(testInfo.getDisplayName(), true, false);
    userDto.setEmail(TestUtility.TEST_EMAIL);

    EqualsVerifier.forClass(PasswordToken.class)
            .withPrefabValues(User.class, UserUtility.getUserFromDto(userDto), UserUtility.getUserFromDto(userDto2))
            .withRedefinedSuperclass()
            .withIgnoredFields(ignoredFields)
            .verify();
  }

}