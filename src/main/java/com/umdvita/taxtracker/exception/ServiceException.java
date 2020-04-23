package com.umdvita.taxtracker.exception;

/**
 * Runtime exception for trapping service errors.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public class ServiceException extends RuntimeException {
  public ServiceException() {
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
