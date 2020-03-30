package com.umdvita.taxtracker;

import com.umdvita.taxtracker.backend.persistence.domain.security.PasswordToken;
import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.backend.service.security.PasswordTokenService;
import com.umdvita.taxtracker.backend.service.security.UserService;
import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.enums.RoleType;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@ActiveProfiles(value = {ProfileType.TEST})
public class TestUtility {

  public static final String[] ENTITY_GLOBAL_FIELDS_TO_IGNORE = {
          "id", "version", "createdAt", "createdBy", "updatedAt", "updatedBy"
  };

  /**
   * Creates and verify user with flexible field creation.
   *
   * @param userService the userService
   * @param username    if a custom user name is needed
   * @param enabled     if the user should be enabled
   * @return persisted user
   */
  protected UserDto createTestUser(UserService userService, String username, boolean enabled) {
    return createTestUser(userService, username, false, false, enabled);
  }

  /**
   * Creates and verify user with flexible field creation.
   *
   * @param userService    the userService
   * @param username       the username
   * @param isAdmin        if the user should be an admin
   * @param requiredFields if all fields should be set
   * @param enabled        if the user should be enabled
   * @return persisted user
   */
  protected UserDto createTestUser(UserService userService, String username, boolean isAdmin, boolean requiredFields,
                                   boolean enabled) {
    UserDto userDto = UserUtility.createTestUser(username, requiredFields, false);

    UserDto storedUserDetails = persistUser(userService, isAdmin, enabled, userDto);
    Assertions.assertNotNull(storedUserDetails);
    Assertions.assertNotNull(storedUserDetails.getId());

    return storedUserDetails;
  }

  protected UserDto persistUser(UserService userService, boolean isAdmin, boolean enabled, UserDto userDto) {
    Set<Role> roles = new HashSet<>();
    roles.add(new Role(isAdmin ? RoleType.ADMIN : RoleType.USER));

    if (enabled) {
      UserUtility.enableUser(userDto);
    }
    return userService.createUser(userDto, roles);
  }

  /**
   * Creates and persists password reset token.
   *
   * @param username             username
   * @param userService          userService
   * @param passwordTokenService the password token service
   * @return the persisted password rest token.
   */
  protected PasswordToken createPasswordToken(String username, UserService userService,
                                              PasswordTokenService passwordTokenService) {
    return createPasswordToken(username, userService, passwordTokenService, -1);
  }

  /**
   * Creates and persists password reset token.
   *
   * @param username             username
   * @param userService          userService
   * @param passwordTokenService the password token service
   * @param period               the expiry duration
   * @return the persisted password rest token.
   */
  protected PasswordToken createPasswordToken(String username, UserService userService,
                                              PasswordTokenService passwordTokenService, int period) {
    UserDto userDto = createTestUser(userService, username, false);
    String token = UUID.randomUUID().toString();
    return passwordTokenService.savePasswordTokenWithUser(token, userDto, period).orElse(null);
  }

  /**
   * Creates and persists password reset token.
   *
   * @param passwordTokenService password token service
   * @param userDto              userDto
   * @return the persisted password rest token.
   */
  protected PasswordToken createPasswordToken(PasswordTokenService passwordTokenService, UserDto userDto) {
    String token = UUID.randomUUID().toString();
    return passwordTokenService.savePasswordTokenWithUser(token, userDto).orElse(null);
  }
}
