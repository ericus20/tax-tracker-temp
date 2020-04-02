package com.umdvita.taxtracker.backend.service.bootstrap;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.backend.service.security.RoleService;
import com.umdvita.taxtracker.backend.service.security.UserService;
import com.umdvita.taxtracker.config.core.properties.ApplicationProperties;
import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.enums.RoleType;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static com.umdvita.taxtracker.constant.UserConstant.ADMIN_USERNAME;
import static com.umdvita.taxtracker.constant.UserConstant.TEST_USERNAME;

/**
 * Initializes the required data for the application like the admin account nd all roles.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
public class DatabaseSeeder implements CommandLineRunner {

  public static final int NUMBER_OF_ADMIN_USERS = 1;
  public static final int NUMBER_OF_LEADERS = 10;
  public static final int NUMBER_OF_VOLUNTEERS = 0;
  public static final int NUMBER_OF_TEST_USERS = 20;

  // turn on/off initializing the database with test data
  private static final boolean ENABLE_TEST_DATA = true;
  private static final boolean ENABLE_TEST_TAX_DATA = true;
  private static final boolean ENABLE_TEST_ACCOUNT_DATA = true;
  private static final boolean ENABLE_TEST_PREPARER_DATA = true;

  private final RoleService roleService;
  private final UserService userService;
  private final Environment environment;
  private final ApplicationProperties properties;
  private int groupIndex = 0;

  /**
   * Constructor for dependency injection.
   *
   * @param roleService           the role service
   * @param userService           the user service
   * @param environment           the environment
   * @param applicationProperties the application properties
   */
  @Autowired
  public DatabaseSeeder(
          RoleService roleService,
          UserService userService,
          Environment environment,
          ApplicationProperties applicationProperties) {
    this.roleService = roleService;
    this.userService = userService;
    this.environment = environment;
    this.properties = applicationProperties;
  }

  /**
   * Initiates and run automatically before the application completes setup for usage.
   *
   * @param args incoming main method arguments
   */
  @Override
  public void run(final String... args) {
    try {
      List<RoleType> roleTypes = Arrays.asList(RoleType.values());
      roleTypes.forEach(roleEnum -> roleService.saveRole(new Role(roleEnum)));

      // persist default admin account
      persistDefaultAdminUser();

      if (ENABLE_TEST_DATA) {
        List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        if (activeProfiles.contains(ProfileType.PROD) || activeProfiles.contains(ProfileType.DOCKER)) {
          String testUserPassword = properties.getTestUserPassword();
          UserDto leader = persistUser("leader", testUserPassword, "admin@ericopoku.com", true, RoleType.USER);
          persistUser("volunteer", testUserPassword, "contact@ericopoku.com", true, RoleType.USER);
          persistUser("client", testUserPassword, "info@ericopoku.com", true, RoleType.USER);
          persistUser("ericus20", testUserPassword, "eric.opoku20@yahoo.com", true, RoleType.ADMIN);
          // Create test accounts
          persistTestingAccounts();
        } else if (activeProfiles.contains(ProfileType.DEV)) {
          // Create test accounts
          persistTestingAccounts();
        }
      }
    } catch (Exception e) {
      LOG.error("There was an error executing run method", e);
    }
  }

  /**
   * Persist testing accounts with a defined set of instances to create.
   */
  private void persistTestingAccounts() {
    if (ENABLE_TEST_ACCOUNT_DATA) {
      createUserInBatch(NUMBER_OF_ADMIN_USERS, true);
      createUserInBatch(NUMBER_OF_TEST_USERS, false);
    }
  }

  /**
   * Accepts the number of user instances to create with a condition if the user should be an admin.
   *
   * @param numberOfUsersToCreate the number of users to create
   * @param isAdmin               if user should be admin
   */
  private void createUserInBatch(final int numberOfUsersToCreate, final boolean isAdmin) {
    if (isAdmin) {
      IntStream.range(0, numberOfUsersToCreate).parallel().forEach(index -> {
        persistUser(ADMIN_USERNAME + index, properties.getWebmasterPassword(), true, RoleType.ADMIN);
      });
    } else {
      List<UserDto> users = new ArrayList<>();
      IntStream
              .range(0, numberOfUsersToCreate)
              .parallel()
              .forEach(index -> {
                String username = index == 1 ? TEST_USERNAME : TEST_USERNAME + index;
                boolean nextBoolean = ThreadLocalRandom.current().nextBoolean();
                UserDto testUser = UserUtility.createTestUser(username, properties.getTestUserPassword(), nextBoolean, nextBoolean);
                users.add(testUser);
              });
      userService.createUsers(users);
    }
  }

  /**
   * Creates a unique admin account with the default email and password.
   */
  private void persistDefaultAdminUser() {
    UserDto userDto = UserUtility
            .createTestUser(properties.getWebmasterUsername(), properties.getWebmasterPassword(), true, true);
    userDto.setEmail(properties.getWebmasterEmail());
    Set<Role> roles = new HashSet<>();
    roles.add(new Role(RoleType.ADMIN));
    roles.add(new Role(RoleType.ENDPOINT_ADMIN));
    userService.createUser(userDto, roles);
  }

  /**
   * Persist user details to the database.
   *
   * @param username       the username
   * @param password       the password
   * @param requiredFields the requiredFields
   * @param roleTypes      the role types
   * @return userDto  the user dto
   */
  private UserDto persistUser(String username, String password, boolean requiredFields, RoleType... roleTypes) {
    return persistUser(username, password, null, requiredFields, roleTypes);
  }

  /**
   * Persist user details to the database.
   *
   * @param username       the username
   * @param password       the password
   * @param email          the email
   * @param requiredFields the requiredFields
   * @param roleTypes      the role types
   * @return userDto  the user dto
   */
  private UserDto persistUser(
          String username, String password, String email, boolean requiredFields, RoleType... roleTypes) {
    UserDto userDto = UserUtility.createTestUser(username, password, requiredFields, true);
    if (Objects.nonNull(email)) {
      userDto.setEmail(email);
    }
    Set<Role> roles = new HashSet<>();
    for (RoleType roleType : roleTypes) {
      roles.add(new Role(roleType));
    }
    return userService.createUser(userDto, roles);
  }
}
