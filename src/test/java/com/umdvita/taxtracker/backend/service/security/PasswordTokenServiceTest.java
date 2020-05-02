package com.umdvita.taxtracker.backend.service.security;

import com.umdvita.taxtracker.TestUtility;
import com.umdvita.taxtracker.backend.persistence.domain.security.PasswordToken;
import com.umdvita.taxtracker.exception.user.UserNotFoundException;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@SpringBootTest
class PasswordTokenServiceTest extends TestUtility {

  @Autowired
  private PasswordTokenService passwordTokenService;

  @Autowired
  private UserService userService;

  @Test
  @DisplayName("savePasswordTokenWithUser")
  void savePasswordTokenWithUser(TestInfo testInfo) {
    PasswordToken passwordToken = createPasswordToken(testInfo.getDisplayName(), userService, passwordTokenService);
    Assertions.assertNotNull(passwordToken);
    Assertions.assertNotNull(passwordToken.getId());
    Assertions.assertNotNull(passwordToken.getUser());
    Assertions.assertNotNull(passwordToken.getUser().getId());
  }

  @Test
  @DisplayName("createTokenForExistingEmail")
  void createTokenForExistingEmail(TestInfo testInfo) {
    PasswordToken passwordToken = createPasswordToken(testInfo.getDisplayName(), userService, passwordTokenService);
    Optional<PasswordToken> token = passwordTokenService.createTokenForEmail(passwordToken.getUser().getEmail());
    Assertions.assertTrue(token.isPresent());
    Optional<PasswordToken> storedPasswordToken = passwordTokenService.getPasswordTokenByToken(token.get().getToken());
    Assertions.assertEquals(storedPasswordToken, token);
  }

  @Test
  @DisplayName("createTokenForNonExistingEmail")
  void createTokenForNonExistingEmail(TestInfo testInfo) {
    Assertions.assertThrows(UserNotFoundException.class,
        () -> passwordTokenService.createTokenForEmail(testInfo.getDisplayName()));
  }

  @Test
  @DisplayName("getPasswordTokenByToken")
  void getPasswordTokenByToken(TestInfo testInfo) {
    PasswordToken passwordToken = createPasswordToken(testInfo.getDisplayName(), userService, passwordTokenService);
    Assertions.assertNotNull(passwordToken);
    Optional<PasswordToken> passwordTokenByToken =
            passwordTokenService.getPasswordTokenByToken(passwordToken.getToken());
    Assertions.assertTrue(passwordTokenByToken.isPresent());
    Assertions.assertNotNull(passwordTokenByToken.get());
    Assertions.assertNotNull(passwordTokenByToken.get().getId());

    passwordToken.setExpiryDate(formatDate(passwordToken.getExpiryDate()));
    passwordTokenByToken.get()
            .setExpiryDate(formatDate(passwordTokenByToken.get().getExpiryDate()));

    Assertions.assertEquals(passwordToken, passwordTokenByToken.get());
  }

  @Test
  @DisplayName("getPasswordTokenById")
  void getPasswordTokenById(TestInfo testInfo) {
    PasswordToken passwordToken = createPasswordToken(testInfo.getDisplayName(), userService, passwordTokenService);
    Assertions.assertNotNull(passwordToken);
    Optional<PasswordToken> passwordTokenById =
            passwordTokenService.getPasswordTokenById(passwordToken.getId());
    Assertions.assertTrue(passwordTokenById.isPresent());
    Assertions.assertNotNull(passwordTokenById.get());
    Assertions.assertNotNull(passwordTokenById.get().getId());

    passwordToken.setExpiryDate(formatDate(passwordToken.getExpiryDate()));
    passwordTokenById.get().setExpiryDate(formatDate(passwordTokenById.get().getExpiryDate()));

    Assertions.assertEquals(passwordToken, passwordTokenById.get());

  }

  @Test
  @DisplayName("deletePasswordToken")
  void deletePasswordToken(TestInfo testInfo) {
    PasswordToken passwordToken = createPasswordToken(testInfo.getDisplayName(), userService, passwordTokenService);
    Assertions.assertNotNull(passwordToken);
    passwordTokenService.deletePasswordToken(passwordToken);
    Assertions.assertFalse(passwordTokenService.getPasswordTokenById(passwordToken.getId()).isPresent());
  }

  @Test
  @DisplayName("deletePasswordTokenByUserEmail")
  void deletePasswordTokenByUserEmail(TestInfo testInfo) {
    PasswordToken passwordToken = createPasswordToken(testInfo.getDisplayName(), userService, passwordTokenService);
    Assertions.assertNotNull(passwordToken);
    Assertions.assertNotNull(passwordToken.getUser());
    passwordTokenService.deleteByUser(passwordToken.getUser().getEmail());
    Assertions.assertFalse(passwordTokenService.getPasswordTokenById(passwordToken.getId()).isPresent());
  }

  @Test
  @DisplayName("deletePasswordTokenByUser")
  void deletePasswordTokenByUser(TestInfo testInfo) {
    PasswordToken passwordToken = createPasswordToken(testInfo.getDisplayName(), userService, passwordTokenService);
    Assertions.assertNotNull(passwordToken);
    Assertions.assertNotNull(passwordToken.getUser());
    passwordTokenService.deleteByUser(UserUtility.getUserDto(passwordToken.getUser()));
    Assertions.assertFalse(passwordTokenService.getPasswordTokenById(passwordToken.getId()).isPresent());
  }

  /**
   * Formats the local date time of each PasswordToken then return it.
   *
   * @param localDateTime the localDateTime
   * @return the localDateTime
   */
  private LocalDateTime formatDate(LocalDateTime localDateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String formatDateTime = localDateTime.format(formatter);
    return LocalDateTime.parse(formatDateTime, formatter);
  }

}