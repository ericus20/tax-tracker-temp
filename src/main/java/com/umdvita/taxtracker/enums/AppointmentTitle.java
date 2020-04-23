package com.umdvita.taxtracker.enums;

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
public enum AppointmentTitle {

  TAX_PREPARATION(1, "TAX_PREPARATION", "Tax Preparation"),
  TAX_STATUS(2, "TAX_STATUS", "Tax Status"),
  OTHER(3, "OTHER", "Other");

  private final int id;
  private final String name;
  private final String text;

  AppointmentTitle(final int roleId, final String roleName, String text) {
    this.id = roleId;
    this.name = roleName;
    this.text = text;
  }
}
