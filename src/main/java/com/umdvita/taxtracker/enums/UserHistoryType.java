package com.umdvita.taxtracker.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * This class holds user history types specified to be supported.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Getter
@ToString
public enum UserHistoryType {

  CREATED("Account created"),
  VERIFIED("Account verified"),
  PASSWORD_UPDATE("Password updated"),
  PROFILE_UPDATE("Profile updated"),
  ASSUME_LEADER("Leader role assigned");
  private final String name;

  UserHistoryType(final String roleName) {
    this.name = roleName;
  }
}
