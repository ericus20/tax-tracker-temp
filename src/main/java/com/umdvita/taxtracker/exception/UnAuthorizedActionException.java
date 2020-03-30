package com.umdvita.taxtracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Processes unauthorized exception.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "You do not have access to this resource")
public class UnAuthorizedActionException extends RuntimeException {
  public UnAuthorizedActionException(String s) {
    super(s);
  }
}
