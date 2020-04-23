package com.umdvita.taxtracker.enums.appointment;

import lombok.Getter;
import lombok.ToString;

/**
 * This class holds profile enumerators specified to be supported.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Getter
@ToString
public enum TimeSlot {

  SIX_SIX_30("18:00", "SIX_SIX_30", 1L),
  SIX_30_SEVEN("18:30", "SIX_30_SEVEN", 2L),
  SEVEN_SEVEN_30("19:00", "SEVEN_SEVEN_30", 3L),
  SEVEN_30_EIGHT("19:30", "SEVEN_30_EIGHT", 4L),
  EIGHT_EIGHT_30("20:00", "EIGHT_EIGHT_30",5L),
  EIGHT_30_NINE("20:30", "EIGHT_30_NINE",6L),
  NINE_NINE_30("21:00", "NINE_NINE_30",7L);

  private final String name;
  private final String key;
  private final Long id;

  TimeSlot(final String name, String key, Long id) {
    this.name = name;
    this.key = key;
    this.id = id;
  }
}
