package com.umdvita.taxtracker.web.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class models the format of the request allowed through the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@ToString(exclude = {"password", "confirmPassword"})
public class UserRequestModel {
  private String name;
  private String userId;
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String confirmPassword;
  private String email;
  private boolean sendNotification;

  public UserRequestModel(boolean sendNotification) {
    this.sendNotification = sendNotification;
  }
}
