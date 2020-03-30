package com.umdvita.taxtracker.constant;

/**
 * Constants used in the UserUtilityConstant of the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public abstract class UtilityConstant {

  public static final String AUTHENTICATION_ERROR_MESSAGE = "Error authenticating user";
  public static final String ERROR = "error";
  public static final String LAST_4_SSN_DECODED = "last4ssnDecoded";
  public static final String CHARACTER_SET = "0123456789abcdefghijklmnopqrstuvwszABCDEFGHIJKLMNOPQRSTUVWXYZ";

  private UtilityConstant() {
    throw new AssertionError("Non Instantiable");
  }
}
