package com.umdvita.taxtracker.web.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class models the format of the response produced in the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class PreparerResponseModel {
  private String username;
  private String name;
  private String email;
  private String phone;
  private boolean leader;
}
