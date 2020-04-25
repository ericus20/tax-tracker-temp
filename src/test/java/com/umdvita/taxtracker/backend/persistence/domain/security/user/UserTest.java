package com.umdvita.taxtracker.backend.persistence.domain.security.user;

import com.umdvita.taxtracker.TestUtility;
import com.umdvita.taxtracker.enums.UserHistoryType;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Unit testing of the user domain model.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
class UserTest {


  private User secondUser;
  private Set<User> setOfUsers;
  private Map<User, Object> mapOfUsers;

  /**
   * Setup test users to be used in the rest of the tests.
   */
  @BeforeEach
  void setUp(TestInfo testInfo) {
    User firstUser = new User();
    firstUser.setUsername(testInfo.getDisplayName());
    firstUser.setEmail(testInfo.getDisplayName().concat(UserUtility.EMAIL));

    secondUser = new User();
    secondUser.setUsername(testInfo.getDisplayName());
    secondUser.setEmail(testInfo.getDisplayName().concat(UserUtility.EMAIL));

    setOfUsers = new HashSet<>();
    setOfUsers.add(firstUser);

    mapOfUsers = new HashMap<>();
    mapOfUsers.put(firstUser, new ArrayList<>());
  }

  @Test
  void testEqualsWithHashSetShouldReturnFalseIfExist() {
    Assertions.assertFalse(setOfUsers.add(secondUser));
  }

  @Test
  void testEqualsWithHashSetShouldReturnTrueOnAddSuccess(TestInfo testInfo) {
    User user3 = new User();
    user3.setUsername(testInfo.getDisplayName());
    user3.setEmail(testInfo.getDisplayName().concat("3@email.com"));

    Assertions.assertTrue(setOfUsers.add(user3));
  }

  @Test
  void testHashCodeWithHashMapsDifferentObjectWithSameKey() {
    Assertions.assertEquals(mapOfUsers.get(secondUser).getClass(), ArrayList.class);
  }

  /**
   * Test that no two users can have same username and email.
   */
  @Test
  void testEqualsAndHashCode(TestInfo testInfo) {

    User user = new User();
    user.setUsername(testInfo.getDisplayName());
    user.setEmail(testInfo.getDisplayName().concat(UserUtility.EMAIL));

    User user2 = new User();
    user2.setUsername(testInfo.getDisplayName());
    user2.setEmail(testInfo.getDisplayName().concat(UserUtility.EMAIL));

    User user3 = new User();
    user3.setUsername(testInfo.getDisplayName().concat("_update"));
    user3.setEmail(testInfo.getDisplayName().concat("@email_update"));

    Set<User> users = new HashSet<>();
    // Test Equals
    Assertions.assertTrue(users.add(user));
    Assertions.assertFalse(users.add(user2));
    Assertions.assertTrue(users.add(user3));

    Map<User, Object> userObjectMap = new HashMap<>();
    // Test HashCode
    userObjectMap.put(user, new ArrayList<>());
    userObjectMap.put(user2, new LinkedList<>());
    userObjectMap.put(user3, new HashMap<>());

    Assertions.assertEquals(userObjectMap.get(user).getClass(), LinkedList.class);
    Assertions.assertEquals(userObjectMap.get(user3).getClass(), HashMap.class);
  }

  @Test
  void equalsContract(TestInfo testInfo) {
    UserDto userDto = UserUtility.createTestUser(testInfo.getDisplayName(), true, false);
    UserDto userDto2 = UserUtility.createTestUser(testInfo.getDisplayName(), true, false);
    userDto.setEmail(TestUtility.TEST_EMAIL);

    UserHistory userHistory = new UserHistory(UserHistoryType.CREATED, UserUtility.getUserFromDto(userDto));
    UserHistory userHistory2 = new UserHistory(UserHistoryType.CREATED, UserUtility.getUserFromDto(userDto2));

    EqualsVerifier.forClass(User.class)
            .withPrefabValues(UserHistory.class, userHistory, userHistory2)
            .withRedefinedSuperclass()
            .verify();
  }

}