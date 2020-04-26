package com.umdvita.taxtracker.backend.persistence.repository.security;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.enums.RoleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles(profiles = {ProfileType.TEST})
class RoleRepositoryTest {

  @Autowired
  private RoleRepository roleRepository;

  @Test
  void findUserRoleByName() {
    Role userRole = new Role(RoleType.USER);
    Role savedUserRole = roleRepository.save(userRole);
    Assertions.assertNotNull(savedUserRole);
    Assertions.assertEquals(userRole, savedUserRole);

    Role roleFromDatabase = roleRepository.findByName(savedUserRole.getName());
    Assertions.assertNotNull(roleFromDatabase);
    Assertions.assertEquals(userRole, roleFromDatabase);
  }

  @Test
  void findAdminRoleById() {
    Role adminRole = new Role(RoleType.ADMIN);
    Role savedUserRole = roleRepository.save(adminRole);
    Assertions.assertNotNull(savedUserRole);
    Assertions.assertEquals(adminRole, savedUserRole);

    Optional<Role> roleFromDatabase = roleRepository.findById(RoleType.ADMIN.getId());
    Assertions.assertTrue(roleFromDatabase.isPresent());
    Assertions.assertEquals(adminRole, roleFromDatabase.get());
  }
}