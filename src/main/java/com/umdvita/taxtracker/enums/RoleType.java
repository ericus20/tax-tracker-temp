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

  ADMIN(1, "ROLE_ADMIN", "ADMIN"),
  LEADER(2, "ROLE_LEADER", "LEADER"),
  VOLUNTEER(3, "ROLE_VOLUNTEER", "VOLUNTEER"),
  USER(4, "ROLE_USER", "USER"),
  CLIENT(5, "ROLE_CLIENT", "CLIENT"),
  ENDPOINT_ADMIN(6, "ROLE_ENDPOINT_ADMIN", "ENDPOINT_ADMIN");

  private final int id;
  private final String name;
  private final String role;
}
