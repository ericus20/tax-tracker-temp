package com.umdvita.taxtracker.backend.service.bootstrap;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.backend.service.security.RoleService;
import com.umdvita.taxtracker.backend.service.security.UserService;
import com.umdvita.taxtracker.config.core.ApplicationProperties;
import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.enums.RoleType;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  public static final int NUMBER_OF_VOLUNTEERS = 5;
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
   * @param roleService the role service
   * @param userService the user service
   * @param environment the environment
   * @param properties  the properties
   */
  @Autowired
  public DatabaseSeeder(
          RoleService roleService,
          UserService userService,
          Environment environment,
          ApplicationProperties properties) {
    this.roleService = roleService;
    this.userService = userService;
    this.environment = environment;
    this.properties = properties;
  }

  /**
   * Initiates and run automatically before the application completes setup for usage.
   *
   * @param args incoming main method arguments
   */
  @Override
  public void run(final String... args) {
    try {
      List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
      populateRoles(activeProfiles);

      // persist default admin account
      persistDefaultAdminUser();

    } catch (Exception e) {
      LOG.error("There was an error executing run method", e);
    }
  }

  /**
   * Initialize the roles before the start of the application except in testing.
   *
   * @param activeProfiles the activeProfiles
   */
  private void populateRoles(List<String> activeProfiles) {
    List<RoleType> roleTypes = Arrays.asList(RoleType.values());
    roleTypes.forEach(roleEnum -> roleService.saveRole(new Role(roleEnum)));
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
}
