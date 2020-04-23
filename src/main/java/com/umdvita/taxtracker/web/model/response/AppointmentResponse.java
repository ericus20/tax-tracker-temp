package com.umdvita.taxtracker.web.model.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class models the format of the appointment response produced in the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
public class AppointmentResponse {
  private String appointmentId;
  private String title;
  private String name;
  private String email;
  private LocalDate date;
  private LocalDateTime start;
  private long numberOfHours;
  private String startString;
  private LocalDateTime end;
  private String endString;
  private String className;
}
