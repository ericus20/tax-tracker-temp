package com.umdvita.taxtracker.backend.service.security;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;

import java.util.Optional;

/**
 * Role service to provide implementation for the definitions about a role.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public interface RoleService {

  /**
   * Create the role with the role instance given.
   *
   * @param role the role
   * @return the persisted role with assigned id
   */
  Optional<Role> saveRole(Role role);

  /**
   * Retrieves the role with the specified id.
   *
   * @param id the id of the role to retrieve
   * @return the role tuple that matches the id given
   */
  Optional<Role> getRoleById(Short id);

  /**
   * Retrieves the role with the specified name.
   *
   * @param name the name of the role to retrieve
   * @return the role tuple that matches the id given
   */
  Optional<Role> getRoleByName(String name);

}
