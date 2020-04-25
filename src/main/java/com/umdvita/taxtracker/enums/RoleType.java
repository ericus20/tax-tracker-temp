package com.umdvita.taxtracker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class holds role enumerators specified to be supported.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum RoleType {

  ADMIN((short) 1, "ROLE_ADMIN", "ADMIN"),
  LEADER((short) 2, "ROLE_LEADER", "LEADER"),
  VOLUNTEER((short) 3, "ROLE_VOLUNTEER", "VOLUNTEER"),
  USER((short) 4, "ROLE_USER", "USER"),
  ENDPOINT_ADMIN((short) 5, "ROLE_ENDPOINT_ADMIN", "ENDPOINT_ADMIN");

  private final short id;
  private final String name;
  private final String role;
}
