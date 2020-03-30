package com.umdvita.taxtracker.enums.appointment;

import lombok.Getter;
import lombok.ToString;

/**
 * This class holds appointment class name enumerators specified to be supported.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Getter
@ToString
public enum AppointmentClassName {

  INFO("bg-info"),
  DANGER("bg-danger"),
  WARNING("bg-warning"),
  SUCCESS("bg-success");

  private final String name;

  AppointmentClassName(final String roleName) {
    this.name = roleName;
  }
}
