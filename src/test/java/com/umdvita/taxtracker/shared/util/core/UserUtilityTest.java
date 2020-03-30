package com.umdvita.taxtracker.shared.util.core;

import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.constant.UserConstant;
import com.umdvita.taxtracker.shared.dto.UserDto;
import org.h2.util.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;

import static com.umdvita.taxtracker.constant.user.ProfileControllerConstant.PIC_SUM_PHOTOS_150_RANDOM;

class UserUtilityTest {

  private static final int LENGTH = 5;
  private static final int DEFAULT_LENGTH = 30;

  @Test
  void generateUserIdWithCustomLength() {
    String userId = UserUtility.generateUserId(LENGTH);
    Assertions.assertEquals(LENGTH, userId.length());
  }

  @Test
  void generateUserIdWithDefaultLength() {
    String userId = UserUtility.generateUserId();
    Assertions.assertEquals(DEFAULT_LENGTH, userId.length());
  }

  @Test
  void getLast4Ssn() {
    String ssn = UserUtility.generateSsn();
    Assertions.assertEquals(9, ssn.length());
    Assertions.assertTrue(StringUtility.isNumeric(ssn));

    String last4Ssn = UserUtility.getLast4Ssn(ssn);
    Assertions.assertEquals(4, last4Ssn.length());

    Assertions.assertEquals(last4Ssn, ssn.substring(5));
  }

  @Test
  void isValidSsnForValidInput() {
    String ssn = "123456789";
    Assertions.assertTrue(UserUtility.isValidSsn(ssn));
  }

  @Test
  void generatePhone() {
    String phone = UserUtility.generatePhone();
    Assertions.assertNotNull(phone);
    Assertions.assertTrue(StringUtils.isNumber(phone));
    Assertions.assertEquals(10, phone.length());
  }

  @Test
  void generateDob() throws ParseException {
    final String generateDob = UserUtility.generateDob();
    Assertions.assertNotNull(generateDob);
    // verify that generated date is in the format MM/dd/yyyy
    DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    sdf.setLenient(false);
    sdf.parse(generateDob);
  }

  @Test
  void getUserDtoFromUser() {
    User user = new User();
    user.setUserId(UserUtility.generateUserId());
    user.setEmail(Thread.currentThread().getName().concat("_@email"));
    UserDto userDto = UserUtility.getUserDto(user);
    Assertions.assertEquals(userDto.getUserId(), user.getUserId());
    Assertions.assertEquals(userDto.getEmail(), user.getEmail());
  }

  @Test
  void generateRandomStringsWithLength() {
    String keyLength = "encryptionIntVec";
    String randomString = UserUtility.generateRandomString(keyLength.length());
    Assertions.assertEquals(keyLength.length(), randomString.length());
  }

  @Test
  void getAuthorizedUserDtoWithAuthNull() {
    final UserDto authorizedUserDto = UserUtility.getAuthorizedUserDto();
    Assertions.assertNull(authorizedUserDto);
  }

  @Test
  void getAuthorizedUserDtoWithAuthNotAuthenticated() {
    Authentication authentication = Mockito.mock(Authentication.class);
    authentication.setAuthenticated(false);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    final UserDto authorizedUserDto = UserUtility.getAuthorizedUserDto();
    Assertions.assertNull(authorizedUserDto);
  }

  @Test
  void getAuthorizedUserDtoWithAuthNotNull() {
    Authentication authentication = Mockito.mock(Authentication.class);
    authentication.setAuthenticated(true);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    final UserDto authorizedUserDto = UserUtility.getAuthorizedUserDto();
    Assertions.assertNull(authorizedUserDto);
  }

  @Test
  void getAuthorizedUserDtoWithAuthNotNullNotAnonymous() {
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    final UserDto authorizedUserDto = UserUtility.getAuthorizedUserDto();
    Assertions.assertNull(authorizedUserDto);
  }

  @Test
  void setProfileImageUrl(TestInfo testInfo) {
    final UserDto userDto = UserUtility.createTestUser(testInfo.getDisplayName(), true, false);
    userDto.setProfileImageUrl(testInfo.getDisplayName());
    Model model = new ExtendedModelMap();
    UserUtility.setUserProfileImage(model, userDto, Collections.emptyList());
    Assertions.assertTrue(model.containsAttribute(UserConstant.PROFILE_IMAGE));
  }

  @Test
  void setProfileImageUrlWithUserProfileEmpty(TestInfo testInfo) {
    final UserDto userDto = UserUtility.createTestUser(testInfo.getDisplayName(), true, false);
    Model model = new ExtendedModelMap();
    UserUtility.setUserProfileImage(model, userDto, Collections.emptyList());
    Assertions.assertTrue(model.containsAttribute(UserConstant.PROFILE_IMAGE));
    Assertions.assertEquals(PIC_SUM_PHOTOS_150_RANDOM, model.getAttribute(UserConstant.PROFILE_IMAGE));
  }

  @Test
  void enableUser() {
    UserDto userDto = new UserDto();
    Assertions.assertFalse(userDto.isEnabled());

    UserUtility.enableUser(userDto);
    Assertions.assertTrue(userDto.isEnabled());
  }

}