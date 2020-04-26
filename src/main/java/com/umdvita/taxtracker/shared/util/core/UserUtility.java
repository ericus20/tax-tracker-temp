package com.umdvita.taxtracker.shared.util.core;

import com.github.javafaker.Faker;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.dto.mapper.UserDtoMapper;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This utility class holds all security methods used in the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public abstract class UserUtility {

  public static final String CHARACTER_SET = "0123456789abcdefghijklmnopqrstuvwszABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final Random RANDOM = new SecureRandom();
  public static final String EMAIL = "@email.com";
  private static final int LENGTH = 30;
  private static final int LAST_4 = 4;
  private static final String UNAUTHORIZED_ACCESS_MESSAGE = "Unauthorized Access detected... User authentication "
          + "is null or anonymous. Returning to login page.";

  private UserUtility() {
  }

  public static String generateRandomId() {
    return generateRandomString(LENGTH);
  }

  public static String generateToken() {
    return generateRandomString(LENGTH);
  }

  public static String generateUserId(int length) {
    return generateRandomString(length);
  }

  public static String generateRandomString(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      String str = CHARACTER_SET;
      stringBuilder.append(str.charAt(RANDOM.nextInt(str.length())));
    }
    return new String(stringBuilder);
  }

  public static String generateSsn() {
    return String.format("%09d", RANDOM.nextInt(1_000_000_000) + 1_000_000_00);
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

  public static boolean isValidSsn(String ssn) {
    if (Objects.isNull(ssn)) {
      return false;
    }
    // SSN could have the form xxx-xx-xxxx and may begin with "," due to input masking
    ssn = ssn.strip().replaceAll("[-,]", "");
    return StringUtils.isNotBlank(ssn) && ssn.length() == 9 && NumberUtils.isCreatable(ssn);
  }

  /**
   * Retrieve the last 4 digits of a valid social number.
   *
   * @param ssn the ssn
   * @return the last 4 digits
   */
  public static String getLast4Ssn(String ssn) {
    InputValidationUtility.validateInputs(ssn);
    if (!isValidSsn(ssn)) {
      throw new IllegalArgumentException("The specified ssn " + ssn + " is not valid");
    }
    return ssn.substring(ssn.length() - LAST_4);
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
    Faker faker = new Faker();
    UserDto userDto = new UserDto();
    userDto.setUsername(username);
    userDto.setPassword(password);
    userDto.setFirstName(faker.name().firstName());
    userDto.setLastName(faker.name().lastName());
    userDto.setName(faker.name().fullName());
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
        userDto.setFirstName(faker.name().firstName());
      }
      if (ThreadLocalRandom.current().nextBoolean()) {
        userDto.setLastName(faker.name().lastName());
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
    List<UserDto> returnValue = UserDtoMapper.MAPPER.toUserDto(storedUserDetails);
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
}
