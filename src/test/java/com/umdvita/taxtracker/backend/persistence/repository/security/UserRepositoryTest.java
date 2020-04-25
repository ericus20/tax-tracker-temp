package com.umdvita.taxtracker.backend.persistence.repository.security;

import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.backend.persistence.repository.RepositoryUtility;
import com.umdvita.taxtracker.constant.ProfileType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles(profiles = {ProfileType.TEST})
class UserRepositoryTest extends RepositoryUtility {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("findByUsername")
  void findByUsername(TestInfo testInfo) {
    User user = getUser(userRepository, testInfo.getDisplayName());
    User storedUser = userRepository.findByUsername(user.getUsername());
    Assertions.assertNotNull(storedUser);
    Assertions.assertEquals(user, storedUser);
  }

  @Test
  @DisplayName("getOneByUsername")
  void getOneByUsername(TestInfo testInfo) {
    User user = getUser(userRepository, testInfo.getDisplayName());
    User storedUser = userRepository.getOneByUsername(user.getUsername());
    Assertions.assertNotNull(storedUser);
    Assertions.assertEquals(user, storedUser);
  }

  @Test
  @DisplayName("findByEmail")
  void findByEmail(TestInfo testInfo) {
    User user = getUser(userRepository, testInfo.getDisplayName());
    User storedUser = userRepository.findByEmail(user.getEmail());
    Assertions.assertNotNull(storedUser);
    Assertions.assertEquals(user, storedUser);
  }

  @Test
  @DisplayName("getOneByEmail")
  void getOneByEmail(TestInfo testInfo) {
    User user = getUser(userRepository, testInfo.getDisplayName());
    User storedUser = userRepository.getOneByEmail(user.getEmail());
    Assertions.assertNotNull(storedUser);
    Assertions.assertEquals(user, storedUser);
  }

  @Test
  @DisplayName("findByToken")
  void findByToken(TestInfo testInfo) {
    User user = getUser(userRepository, testInfo.getDisplayName());
    User storedUser = userRepository.findByToken(user.getEmail());
    Assertions.assertNotNull(storedUser);
    Assertions.assertEquals(user, storedUser);
  }

  @Test
  @DisplayName("getOneByToken")
  void getOneByToken(TestInfo testInfo) {
    User user = getUser(userRepository, testInfo.getDisplayName());
    User storedUser = userRepository.getOneByToken(user.getEmail());
    Assertions.assertNotNull(storedUser);
    Assertions.assertEquals(user, storedUser);
  }
}