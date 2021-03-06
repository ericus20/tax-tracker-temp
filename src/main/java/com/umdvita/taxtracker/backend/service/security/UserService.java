package com.umdvita.taxtracker.backend.service.security;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

/**
 * This is the contract for the user service operations.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public interface UserService extends UserDetailsService {

  /**
   * Create the user with the user details given.
   *
   * @param userDto   the user with updated information
   * @param roles     the user roles assigned to the userDto
   * @return          the created userDto.
   * @throws IllegalArgumentException in case the given entity is {@literal null}
   */
  UserDto createUser(UserDto userDto, Set<Role> roles);

  /**
   * Create the user with the user details given.
   *
   * @param userDto   the user with updated information
   * @return the created userDto.
   * @throws IllegalArgumentException in case the given entity is {@literal null}
   */
  UserDto createUser(UserDto userDto);

  /**
   * Creates the list of users provided in batch.
   *
   * @param userDtos the userDtos
   * @return list of created userDtos
   */
  List<UserDto> createUsers(List<UserDto> userDtos);

  /**
   * Saves or updates the userDto with the userDto instance given.
   *
   * @param userDto the userDto with updated information
   * @return the updated userDto.
   * @throws IllegalArgumentException in case the given entity is
   *                                  {@literal null}
   */
  UserDto saveOrUpdate(UserDto userDto);

  /**
   * Returns a user for the given username or null if a user could not be found.
   *
   * @param username The username associated to the user to find
   * @return a user for the given username or null if a user could not be found
   * @throws IllegalArgumentException in case the given entity is
   *                                  {@literal null}
   */
  UserDto getUserByUsername(String username);

  /**
   * Returns a lazily loaded user for the given username or null if a user could not be found.
   *
   * @param username The username associated to the user to find.
   * @return a user for the given username or null if a user could not be found
   * @throws IllegalArgumentException in case the given entity is
   *                                  {@literal null}
   */
  UserDto getUserReferenceByUsername(String username);
}
