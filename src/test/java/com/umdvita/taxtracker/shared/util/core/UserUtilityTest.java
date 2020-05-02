package com.umdvita.taxtracker.shared.util.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserUtilityTest {

  @Test
  void isValidSsn() {
    String ssn = "060-98-3692";
    Assertions.assertTrue(UserUtility.isValidSsn(ssn));
  }
}