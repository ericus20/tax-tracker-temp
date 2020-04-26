package com.umdvita.taxtracker.backend.service.security.impl;

import com.umdvita.taxtracker.annotation.Loggable;
import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.UserHistory;
import com.umdvita.taxtracker.backend.persistence.repository.security.UserRepository;
import com.umdvita.taxtracker.backend.service.security.EncryptionService;
import com.umdvita.taxtracker.backend.service.security.UserService;
import com.umdvita.taxtracker.enums.RoleType;
import com.umdvita.taxtracker.enums.UserHistoryType;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The UserServiceImpl class provides implementation for the UserService definitions.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

  private final EncryptionService encryptionService;
  private final UserRepository userRepository;

  // Thread to encrypt the password
  private Thread encryptPassword;

  @Autowired
  public UserServiceImpl(EncryptionService encryptionService, UserRepository userRepository) {
    this.encryptionService = encryptionService;
    this.userRepository = userRepository;
  }

  /**
   * Create the user with the user details given.
   *
   * @param userDto the user with updated information
   * @param roles   the user roles assigned to the userDto
   * @return the updated userDto.
   * @throws IllegalArgumentException in case the given entity is {@literal null}
   */
  @Loggable
  @Override
  @Transactional
  public UserDto createUser(UserDto userDto, Set<Role> roles) {
    InputValidationUtility.validateInputs(getClass(), userDto);

    User localUser = userRepository.getOneByEmail(userDto.getEmail());
    if (Objects.nonNull(localUser)) {
      // if user is not enabled, they might have skipped verification and trying to sign-up again.
      if (!localUser.isEnabled()) {
        LOG.debug("Email {} already exists but not enabled. User: {}", userDto.getEmail(), localUser);
        return UserUtility.getUserDto(localUser);
      }
      LOG.warn("Email {} already exist and nothing will be done", userDto.getEmail());
    } else {
      // setup the name and other details provided by the user
      preparerUserDetails(userDto);
      // transfer user details to a user object then persist to database.
      return persistUserDetails(userDto, roles, UserHistoryType.CREATED);
    }
    return null;
  }

  /**
   * Create the user with the user details given.
   *
   * @param userDto the user with updated information
   * @return the updated userDto.
   * @throws IllegalArgumentException in case the given entity is {@literal null}
   */
  @Loggable
  @Override
  @Transactional
  public UserDto createUser(UserDto userDto) {
    return createUser(userDto, null);
  }

  /**
   * Creates the list of users provided in batch.
   *
   * @param userDtos the userDtos
   * @return list of created userDtos
   */
  @Override
  @Transactional
  public List<UserDto> createUsers(List<UserDto> userDtos) {
    List<User> users = new ArrayList<>();
    userDtos.forEach(userDto -> {
      if (!userRepository.existsByEmailOrderById(userDto.getEmail())) {
        preparerUserDetails(userDto);
        User localUser = UserUtility.getUserFromDto(userDto);
        localUser.addUserHistory(new UserHistory(UserHistoryType.CREATED, localUser));
        localUser.setRoles(Collections.singleton(new Role(RoleType.USER)));
        users.add(localUser);
      }
    });
    List<User> savedUsers = userRepository.saveAll(users);
    return UserUtility.getUserDto(savedUsers);
  }

  /**
   * Returns a user for the given username or null if a user could not be found.
   *
   * @param username The username associated to the user to find
   * @return a user for the given username or null if a user could not be found
   * @throws IllegalArgumentException in case the given entity is
   *                                  {@literal null}
   */
  @Override
  public UserDto getUserByUsername(String username) {
    InputValidationUtility.validateInputs(getClass(), username);
    User storedUserDetails = userRepository.findByUsername(username);
    if (Objects.isNull(storedUserDetails)) {
      return null;
    }
    return UserUtility.getUserDto(storedUserDetails);
  }

  /**
   * Returns a lazily loaded user for the given username or null if a user could not be found.
   *
   * @param username The username associated to the user to find.
   * @return a user for the given username or null if a user could not be found
   * @throws IllegalArgumentException in case the given entity is
   *                                  {@literal null}
   */
  @Override
  public UserDto getUserReferenceByUsername(String username) {
    InputValidationUtility.validateInputs(getClass(), username);
    User storedUserDetails = userRepository.getOneByUsername(username);
    if (Objects.isNull(storedUserDetails)) {
      return null;
    }
    return UserUtility.getUserDto(storedUserDetails);
  }

  /**
   * Locates the user based on the username. In the actual implementation, the search
   * may possibly be case sensitive, or case insensitive depending on how the
   * implementation instance is configured. In this case, the <code>UserDetails</code>
   * object that comes back may have a username that is of a different case than what
   * was actually requested..
   *
   * @param email the username identifying the user whose data is required.
   * @return a fully populated user record (never <code>null</code>)
   * @throws UsernameNotFoundException if the user could not be found or the user has no
   *                                   GrantedAuthority
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    // Ensure that username (email) is not empty or null.
    if (StringUtils.isNotBlank(email)) {
      User user = userRepository.getOneByEmail(email);
      if (Objects.isNull(user)) {
        LOG.warn("No record found for user with email {}", email);
        throw new UsernameNotFoundException("User with email " + email + " not found");
      }
      List<SimpleGrantedAuthority> authorities = user.getRoles()
              .stream()
              .map(role -> new SimpleGrantedAuthority(role.getName()))
              .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
              authorities);
    }
    return null;
  }

  /**
   * Setup the name and other details provided by the user.
   *
   * @param userDto the userDto
   */
  private void preparerUserDetails(UserDto userDto) {
    encryptPassword = new Thread(() -> userDto.setPassword(encryptionService.encryptText(userDto.getPassword())));
    encryptPassword.start();
    userDto.setToken(UserUtility.generateToken());
    if (UserUtility.isValidSsn(userDto.getSsn())) {
      userDto.setLast4Ssn(encryptionService.encodeText(UserUtility.getLast4Ssn(userDto.getSsn())));
      userDto.setSsn(encryptionService.encodeText(userDto.getSsn()));
    }
    if (StringUtils.isNotBlank(userDto.getFirstName()) && StringUtils.isNotBlank(userDto.getLastName())) {
      userDto.setFirstName(StringUtils.capitalize(userDto.getFirstName()));
      userDto.setLastName(StringUtils.capitalize(userDto.getLastName()));
      userDto.setName(String.join(" ", userDto.getFirstName(), userDto.getLastName()));
    }
    executeThreadJoin();
  }

  /**
   * Transfers user details to a user object then persist to database.
   *
   * @param userDto         the userDto
   * @param roles           the roles
   * @param userHistoryType the user history type
   * @return the userDto
   */
  private UserDto persistUserDetails(UserDto userDto, Set<Role> roles, UserHistoryType userHistoryType) {
    User localUser = UserUtility.getUserFromDto(userDto);
    localUser.addUserHistory(new UserHistory(userHistoryType, localUser));
    if (Objects.isNull(roles) || roles.isEmpty()) {
      roles = Collections.singleton(new Role(RoleType.USER));
    }
    localUser.setRoles(roles);
    localUser = userRepository.save(localUser);
    LOG.debug("User successfully persisted as {}", localUser);
    return UserUtility.getUserDto(localUser);
  }

  private void executeThreadJoin() {
    try {
      if (Objects.nonNull(encryptPassword)) {
        encryptPassword.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
