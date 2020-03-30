package com.umdvita.taxtracker.shared.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * The UserDto transfers user details from outside into the application and vice versa.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@ToString(exclude = {"password", "ssn"})
@EqualsAndHashCode(of = {"username", "email", "userId"}, callSuper = false)
public class UserDto extends BaseDto implements Serializable {
  private static final long serialVersionUID = -2423636754258148802L;

  private String userId;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String name;
  private String dob;
  private String email;
  private String ssn;
  private String last4Ssn;
  private String phone;
  private String profileImageUrl;
  private String preSignedProfileImageUrl;
  private boolean enabled;
}
