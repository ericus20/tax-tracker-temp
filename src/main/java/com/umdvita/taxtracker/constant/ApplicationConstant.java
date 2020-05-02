package com.umdvita.taxtracker.constant;

/**
 * This class holds all constants used by the Application.
 *
 * @author Eric Opoku
 */
public abstract class ApplicationConstant {
  public static final String ASSERTION_ERROR_MESSAGE = "Non Instantiable";

  private ApplicationConstant() {
    throw new AssertionError(ASSERTION_ERROR_MESSAGE);
  }
}
