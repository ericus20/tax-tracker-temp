package com.umdvita.taxtracker.backend.service.security;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.enums.RoleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles(value = {ProfileType.TEST})
class RoleServiceTest {

  @Autowired
  protected RoleService roleService;

  @Test
  void saveRole() {
    Optional<Role> optionalRole = roleService.saveRole(new Role(RoleType.USER));
    Assertions.assertTrue(optionalRole.isPresent());
  }

  @Test
  void getRoleById() {
    Optional<Role> optionalRole = roleService.saveRole(new Role(RoleType.USER));
    Assertions.assertTrue(optionalRole.isPresent());

    Optional<Role> roleById = roleService.getRoleById(optionalRole.get().getId());
    Assertions.assertTrue(roleById.isPresent());
    Assertions.assertEquals(optionalRole.get(), roleById.get());
  }

  @Test
  void getRoleByName() {
    Optional<Role> optionalRole = roleService.saveRole(new Role(RoleType.USER));
    Assertions.assertTrue(optionalRole.isPresent());

    Optional<Role> roleByName = roleService.getRoleByName(optionalRole.get().getName());
    Assertions.assertTrue(roleByName.isPresent());
    Assertions.assertEquals(optionalRole.get(), roleByName.get());
  }
}