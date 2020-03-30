package com.umdvita.taxtracker.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Responsible for user not found exception specifically.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User does not exist")
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String s) {
    super(s);
  }
}
