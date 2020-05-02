package com.umdvita.taxtracker.backend.service.security.impl;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.backend.persistence.repository.security.RoleRepository;
import com.umdvita.taxtracker.backend.service.security.RoleService;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The RoleServiceImpl class is an implementation for the RoleService Interface.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

  /**
   * The role repository interface.
   */
  private final RoleRepository roleRepository;

  /**
   * The controller for injection.
   *
   * @param roleRepository the role repository
   */
  @Autowired
  public RoleServiceImpl(final RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  /**
   * Create the role with the role instance given.
   *
   * @param role the role to createQuestion
   * @return the persisted role with assigned id
   */
  @Override
  @Transactional
  public Optional<Role> saveRole(final Role role) {
    return Optional.of(roleRepository.save(role));
  }

  /**
   * Retrieves the role with the specified id.
   *
   * @param id the id of the role to retrieve
   * @return the role tuple that matches the id given
   */
  @Override
  public Optional<Role> getRoleById(final Short id) {
    return roleRepository.findById(id);
  }

  /**
   * Retrieves the role with the specified name.
   *
   * @param name the name of the role to retrieve
   * @return the role tuple that matches the id given
   */
  @Override
  public Optional<Role> getRoleByName(final String name) {
    InputValidationUtility.validateInputs(getClass(), name);
    return Optional.of(roleRepository.findByName(name));
  }
}
