package com.umdvita.taxtracker.backend.persistence.repository;

import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.backend.persistence.repository.security.UserRepository;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import org.junit.jupiter.api.Assertions;

public abstract class RepositoryUtility {

  protected static User getUser(UserRepository userRepository, String username) {
    UserDto userDto = UserUtility.createTestUser(username, username, true, false);
    User user = UserUtility.getUserFromDto(userDto);
    user.setToken(UserUtility.generateToken());
    user.setCreatedBy(userDto.getUsername());
    user.setUpdatedBy(userDto.getUsername());
    User storedUser = userRepository.save(user);
    Assertions.assertNotNull(storedUser);
    Assertions.assertNotNull(storedUser.getId());
    return storedUser;
  }
}
