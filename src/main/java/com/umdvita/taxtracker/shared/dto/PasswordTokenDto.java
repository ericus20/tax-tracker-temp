package com.umdvita.taxtracker.shared.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The UserDto transfers user details from outside into the application and vice versa.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"token"}, callSuper = false)
public class PasswordTokenDto extends BaseDto implements Serializable {
  private static final long serialVersionUID = -2423636754258148802L;

  private String token;
  private LocalDateTime expiryDate;
  private UserUpdateDto user;
}
