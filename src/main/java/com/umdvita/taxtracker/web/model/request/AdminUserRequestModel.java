package com.umdvita.taxtracker.web.model.request;

import lombok.Data;
import lombok.ToString;

/**
 * This class models the format of the admin request allowed through the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@ToString(exclude = {"password", "confirmPassword"})
public class AdminUserRequestModel {
  private String name;
  private String email;
  private String phone;
  private String username;
  private String password;
  private String confirmPassword;
  private String role;
  private String group;
}
