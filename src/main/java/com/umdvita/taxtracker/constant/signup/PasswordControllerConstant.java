package com.umdvita.taxtracker.constant.signup;

/**
 * This class holds all constants used in PasswordToken implementations.
 *
 * @author Eric Opoku
 */
public abstract class PasswordControllerConstant {

  /**
   * URL Mapping Constants for forget password path.
   */
  public static final String FORGOT_ROOT_MAPPING = "/user/forgot/";
  /**
   * URL Mapping Constants for change password path.
   */
  public static final String CHANGE_PATH = "change";
  /**
   * View Name Constant for email form.
   */
  public static final String EMAIL_ADDRESS_VIEW_NAME = "user/email-form";
  /**
   * View Name Constant for change password.
   */
  public static final String CHANGE_VIEW_NAME = "user/password-reset-form";
  /**
   * Model Key Constant for email success.
   */
  public static final String EMAIL_SENT_KEY = "emailSent";
  public static final String EMAIL_ERROR_KEY = "emailError";
  public static final String USER_REQUEST_MODEL_KEY_NAME = "userRequestModel";
  /**
   * Model Key Constant for password rest text key.
   */
  public static final String CHANGE_PATH_KEY = "change.user.password.path";
  public static final String EMAIL_MESSAGE_TEXT_PROPERTY_KEY = "forgot.my.password.email.text";
  public static final String TOKEN_INVALID = "reset.password.token.invalid";
  public static final String TOKEN_EXPIRED = "reset.password.token.expired";
  public static final String NOT_AUTHORIZED = "operation.not.authorized.text";
  /**
   * Model Key Constant for forget password attribute name.
   */
  public static final String RESET_ATTRIBUTE_NAME = "passwordReset";
  /**
   * Model Key Constant message.
   */
  public static final String RESET_ERROR_MESSAGE = "errorMessage";
  public static final String ACCOUNT_IN_SESSION = "Account is currently in session!";
  public static final String RESET_ERROR = "An error processing password reset";

  /**
   * Constructor for Password Token Constants made private.
   */
  private PasswordControllerConstant() {
  }
}
