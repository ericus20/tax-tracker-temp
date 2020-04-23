package com.umdvita.taxtracker.enums.appointment;

import lombok.Getter;
import lombok.ToString;

/**
 * This class holds appointment status enumerators specified to be supported.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Getter
@ToString
public enum AppointmentStatus {

  AVAILABLE(1, "AVAILABLE"),
  BOOKED(2, "BOOKED");

  private final int id;
  private final String name;

  AppointmentStatus(final int roleId, final String roleName) {
    this.id = roleId;
    this.name = roleName;
  }
}
