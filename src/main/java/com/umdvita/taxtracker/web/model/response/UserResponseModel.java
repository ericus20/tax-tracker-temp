package com.umdvita.taxtracker.web.model.response;

import lombok.Data;

/**
 * This class models the format of the response produced in the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
public class UserResponseModel {
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String role;
}
