package com.umdvita.taxtracker.shared.util.core;

import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.constant.UserConstant;
import com.umdvita.taxtracker.constant.UtilityConstant;
import com.umdvita.taxtracker.constant.user.ProfileControllerConstant;
import com.umdvita.taxtracker.enums.RoleType;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.dto.mapper.UserDtoMapper;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.umdvita.taxtracker.constant.user.ProfileControllerConstant.PIC_SUM_PHOTOS_150_RANDOM;

/**
 * This utility class holds all security methods used in the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public abstract class UserUtility {

  public static final Random RANDOM = new SecureRandom();
  public static final String EMAIL = "@email.com";
  private static final int LENGTH = 30;
  private static final int LAST_4 = 4;
  private static final String UNAUTHORIZED_ACCESS_MESSAGE = "Unauthorized Access detected... User authentication "
          + "is null or anonymous. Returning to login page.";

  private static List<String> firstNames = Arrays.asList(
          "John", "Mark", "Sean", "James", "Luke", "Dan", "Abigail",
          "Alexandra", "Alison", "Amanda", "Amelia", "Amy", "Andrea", "Angela", "Anna");
  private static List<String> lastNames = Arrays.asList(
          "Doe", "Smith", "Johnson", "Simmons", "Kelly", "McKey", "Bell",
          "Abraham", "Allan", "Alsop", "Arnold", "Kelly", "Bailey");

  private UserUtility() {
  }

  public static String generateRandomId() {
    return generateRandomString(LENGTH);
  }

  public static String generateUserId() {
    return generateRandomId();
  }

  public static String generateUserId(int length) {
    return generateRandomString(length);
  }

  public static String generateSsn() {
    return String.format("%09d", RANDOM.nextInt(1000000000));
  }

  public static String generatePhone() {
    return String.valueOf((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
  }

  public static String generateDob() {
    int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
    int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
    long randomDay = minDay + (long) RANDOM.nextInt(maxDay - minDay);
    DateTimeFormatter inFormat = DateTimeFormat.forPattern("yyyy-mm-dd");
    DateTime dateTime = inFormat.parseDateTime(LocalDate.ofEpochDay(randomDay).toString());
    DateTimeFormatter outFormat = DateTimeFormat.forPattern("MM/dd/yyyy");
    return outFormat.print(dateTime);
  }

  /**
   * Create a test user with flexibility.
   *
   * @param username       the username
   * @param password       the password
   * @param requiredFields the required fields
   * @param enable         if user should be enabled
   * @return the user dto
   */
  public static UserDto createTestUser(String username, String password, boolean requiredFields, boolean enable) {
    UserDto userDto = new UserDto();
    userDto.setUsername(username);
    userDto.setPassword(password);
    userDto.setFirstName(firstNames.get(RANDOM.nextInt(firstNames.size())));
    userDto.setLastName(lastNames.get(RANDOM.nextInt(lastNames.size())));
    userDto.setName(String.join(" ", userDto.getFirstName(), userDto.getLastName()));
    userDto.setEmail(username.concat(EMAIL));
    if (requiredFields) {
      userDto.setSsn(UserUtility.generateSsn());
      userDto.setDob(UserUtility.generateDob());
      userDto.setPhone(UserUtility.generatePhone());
    } else {
      if (ThreadLocalRandom.current().nextBoolean()) {
        userDto.setSsn(UserUtility.generateSsn());
      }
      if (ThreadLocalRandom.current().nextBoolean()) {
        userDto.setFirstName(firstNames.get(RANDOM.nextInt(firstNames.size())));
      }
      if (ThreadLocalRandom.current().nextBoolean()) {
        userDto.setLastName(lastNames.get(RANDOM.nextInt(lastNames.size())));
      }
    }
    if (enable) {
      enableUser(userDto);
    }
    return userDto;
  }

  /**
   * Create a test user with flexibility.
   *
   * @param username       the username
   * @param requiredFields the required fields
   * @param enable         if user should be enabled
   * @return the user dto
   */
  public static UserDto createTestUser(String username, boolean requiredFields, boolean enable) {
    return createTestUser(username, "", requiredFields, enable);
  }

  /**
   * Enables and unlocks a user.
   *
   * @param userDto the userDto
   */
  public static void enableUser(UserDto userDto) {
    InputValidationUtility.validateInputs(userDto);
    userDto.setEnabled(true);
  }

  /**
   * Enables and unlocks a user.
   *
   * @param user the user
   */
  public static void enableUser(User user) {
    InputValidationUtility.validateInputs(user);
    user.setEnabled(true);
  }

  public static boolean isValidSsn(String ssn) {
    if (Objects.isNull(ssn)) {
      return false;
    }
    // SSN could have the form xxx-xx-xxxx and may preceed with "," due to input masking
    ssn = ssn.strip().replaceAll("[-,]", "");
    return StringUtils.isNotBlank(ssn) && ssn.length() == 9 && StringUtility.isNumeric(ssn);
  }

  public static String getLast4Ssn(String ssn) {
    InputValidationUtility.validateInputs(ssn);
    return ssn.substring(ssn.length() - LAST_4);
  }

  public static List<String> getEmailsFromUser(List<UserDto> userDtos) {
    List<String> emails = new ArrayList<>();
    userDtos.forEach(userDto -> emails.add(getEmailsFromUser(userDto)));
    return emails;
  }

  public static String getEmailsFromUser(UserDto userDto) {
    InputValidationUtility.validateInputs(userDto);
    return userDto.getEmail();
  }

  /**
   * Transfers data from entity to returnable object.
   *
   * @param storedUserDetails stored user details
   * @return user dto
   */
  public static UserDto getUserDto(User storedUserDetails) {
    InputValidationUtility.validateInputs(storedUserDetails);
    return UserDtoMapper.MAPPER.toUserDto(storedUserDetails);
  }

  /**
   * Transfers data from list of entity to returnable list.
   *
   * @param storedUserDetails stored user details
   * @return list of user dto
   */
  public static List<UserDto> getUserDto(List<User> storedUserDetails) {
    InputValidationUtility.validateInputs(storedUserDetails);
    List<UserDto> returnValue = new ArrayList<>();
    for (User storedUserDetail : storedUserDetails) {
      returnValue.add(getUserDto(storedUserDetail));
    }
    returnValue.sort(Comparator.comparing(UserDto::getFirstName, Comparator.nullsLast(Comparator.naturalOrder()))
            .thenComparing(UserDto::getLastName, Comparator.nullsLast(Comparator.naturalOrder())));
    return returnValue;
  }

  /**
   * Transfers data from entity to returnable object.
   *
   * @param userDto the userDto
   * @return user
   */
  public static User getUserFromDto(UserDto userDto) {
    InputValidationUtility.validateInputs(userDto);
    return UserDtoMapper.MAPPER.toUser(userDto);
  }

  public static String generateRandomString(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      String str = UtilityConstant.CHARACTER_SET;
      stringBuilder.append(str.charAt(RANDOM.nextInt(str.length())));
    }
    return new String(stringBuilder);
  }

  public static UserDto getPopulatedUserDto(Model model, Environment environment) {
    UserDto authorizedUserDto = getAuthorizedUserDto();
    model.addAttribute(ProfileControllerConstant.DEFAULT, true);
    if (Objects.isNull(authorizedUserDto)) {
      LOG.warn(UNAUTHORIZED_ACCESS_MESSAGE);
      SecurityContextHolder.clearContext();
      SecurityContextHolder.getContext().setAuthentication(null);
      model.addAttribute(UtilityConstant.ERROR, true);
    } else {
      // set the appropriate user profile image now.
      setUserProfileImage(model, authorizedUserDto, Arrays.asList(environment.getActiveProfiles()));
    }
    return authorizedUserDto;
  }

  public static void setUserProfileImage(Model model, UserDto userDto, List<String> activeProfiles) {
    if (StringUtils.isBlank(userDto.getProfileImageUrl())) {
      model.addAttribute(UserConstant.PROFILE_IMAGE, PIC_SUM_PHOTOS_150_RANDOM);
    } else if (activeProfiles.contains(ProfileType.PROD) || activeProfiles.contains(ProfileType.PROD_AWS)) {
      model.addAttribute(UserConstant.PROFILE_IMAGE, userDto.getPreSignedProfileImageUrl());
    } else {
      model.addAttribute(UserConstant.PROFILE_IMAGE, userDto.getProfileImageUrl());
    }
    model.addAttribute(UserConstant.USER_MODEL_KEY, userDto);
    if (model.containsAttribute(ProfileControllerConstant.NEW_PROFILE)) {
      model.addAttribute(ProfileControllerConstant.DEFAULT, false);
    }
  }

  public static UserDto getAuthorizedUserDto() {
    User authorizedUser = getAuthorizedUser();
    if (Objects.isNull(authorizedUser)) {
      return null;
    }
    return getUserDto(authorizedUser);
  }

  public static boolean isUserUnauthorized(Model model, Environment environment) {
    UserDto authorizedUser = getAuthorizedUserDto();
    if (authorizedUser == null) {
      LOG.warn("Unauthorized Access to method... Returning to error page.");
      model.addAttribute(ProfileControllerConstant.ERROR, "true");
      return true;
    }
    // set the appropriate user profile image now.
    if (Objects.nonNull(environment)) {
      setUserProfileImage(model, authorizedUser, Arrays.asList(environment.getActiveProfiles()));
    }
    return false;
  }

  private static User getAuthorizedUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(auth) || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
      LOG.warn(UNAUTHORIZED_ACCESS_MESSAGE);
      return null;
    }
    return (User) auth.getPrincipal();
  }

  public static RoleType getRoleType(String roleName) {
    InputValidationUtility.validateInputs(roleName);
    for (RoleType value : RoleType.values()) {
      if (value.getRole().equals(roleName)) {
        return value;
      }
    }
    return RoleType.USER;
  }
}
