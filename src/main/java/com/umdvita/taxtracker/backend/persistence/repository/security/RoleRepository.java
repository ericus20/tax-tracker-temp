package com.umdvita.taxtracker.backend.persistence.repository.security;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The repository or DAO for role domain model.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Repository
@Transactional(readOnly = true)
public interface RoleRepository extends CrudRepository<Role, Short> {

  /**
   * Finds and return the role by it's name.
   *
   * @param name the name
   * @return the role.
   */
  Role findByName(String name);
}
