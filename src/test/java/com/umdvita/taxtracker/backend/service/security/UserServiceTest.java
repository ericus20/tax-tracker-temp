package com.umdvita.taxtracker.backend.service.security;

import com.umdvita.taxtracker.TestUtility;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest extends TestUtility {

  @Autowired
  private UserService userService;

  /**
   * Test attempts to create a user, verify that user has been created successfully by checking
   * it's generated id assigned to it.
   */
  @Test
  @DisplayName("createUser")
  void createUser(TestInfo testInfo) {
    UserDto userDto = createTestUser(userService, testInfo.getDisplayName(), false);
    Assertions.assertNotNull(userDto);
    Assertions.assertNotNull(userDto.getId());
  }

  /**
   * Test attempts to create a user, verify that user has been created successfully by checking
   * it's generated id assigned to it.
   */
  @Test
  @DisplayName("createUserWithoutRole")
  void createUserWithoutRole(TestInfo testInfo) {
    UserDto userDto = UserUtility.createTestUser(testInfo.getDisplayName(), testInfo.getDisplayName(),false, false);
    UserDto storedUserDto = userService.createUser(userDto);
    Assertions.assertNotNull(storedUserDto);
    Assertions.assertNotNull(storedUserDto.getId());
  }

  /**
   * Creating a user who exists and not enabled should return the existing user.
   */
  @Test
  @DisplayName("createUserAlreadyExistingAndNotEnabled")
  void createUserAlreadyExistingAndNotEnabled(TestInfo testInfo) {
    UserDto userDto = createTestUser(userService, testInfo.getDisplayName(), false);
    UserDto existingUser = createTestUser(userService, testInfo.getDisplayName(), false);
    Assertions.assertEquals(userDto.getId(), existingUser.getId());
  }

  /**
   * Creating a user who exists and enabled should return null.
   */
  @Test
  @DisplayName("createUserAlreadyExistingAndEnabled")
  void createUserAlreadyExistingAndEnabled(TestInfo testInfo) {
    UserDto userDto = createTestUser(userService, testInfo.getDisplayName(), true);
    Assertions.assertNotNull(userDto);
    UserDto existingUser = persistUser(userService, false, true, userDto);
    Assertions.assertNull(existingUser);
  }

  /**
   * Test checks that any null input passed to the function should be rejected.
   */
  @Test
  void testCreateUserThrowsExceptionOnNullInput() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUser(null, null));
  }

  /**
   * Test checks that an existing user can be retrieved using the username provided.
   */
  @Test
  @DisplayName("getUserByUsername")
  void getUserByUsername(TestInfo testInfo) {
    UserDto userDto = createTestUser(userService, testInfo.getDisplayName(), false);
    UserDto userByUsername = userService.getUserByUsername(userDto.getUsername());
    Assertions.assertNotNull(userByUsername);
    Assertions.assertNotNull(userByUsername.getId());
  }

  /**
   * Test checks that an existing user can be retrieved using the username provided.
   */
  @Test
  @DisplayName("getUserReferenceByUsername")
  void getUserReferenceByUsername(TestInfo testInfo) {
    UserDto userDto = createTestUser(userService, testInfo.getDisplayName(), false);
    UserDto userByUsername = userService.getUserReferenceByUsername(userDto.getUsername());
    Assertions.assertNotNull(userByUsername);
    Assertions.assertNotNull(userByUsername.getId());
  }

  @Test
  @DisplayName("getUserByUsernameNotExisting")
  void getUserByUsernameNotExisting(TestInfo testInfo) {
    UserDto userDto = createTestUser(userService, testInfo.getDisplayName(), false);
    UserDto userByUsername = userService.getUserByUsername(userDto.getUsername().concat("-"));
    Assertions.assertNull(userByUsername);
  }

  @Test
  void testGetUserGivenUsernameThrowsExceptionOnNullInput() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> userService.getUserByUsername(null));
  }

}