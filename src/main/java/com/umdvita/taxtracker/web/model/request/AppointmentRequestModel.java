package com.umdvita.taxtracker.web.model.request;

import lombok.Data;

/**
 * This class models the format of the request allowed for appointments through the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
public class AppointmentRequestModel {
  private String id;
}
