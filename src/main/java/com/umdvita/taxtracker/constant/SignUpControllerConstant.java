package com.umdvita.taxtracker.constant;

/**
 * This class holds all constants used in SignUpController implementations.
 *
 * @author Eric Opoku
 */
public abstract class SignUpControllerConstant {

  /**
   * Generic Constants.
   */
  public static final String ERROR_CREATING_USER = "Error creating user";
  public static final String EMAIL_LINK = "link";
  public static final String IMAGE_URL = "imageUrl";
  public static final String IMAGE_URL_INLINE = "imageUrlInline";
  public static final String EMAIL_VERIFY_TEMPLATE = "email/verify-email";
  public static final String MAIL_SUCCESS_MESSAGE = "Mail successfully sent!";
  public static final String MAIL_ERROR_MESSAGE = "There was an error sending mail!";
  public static final String INVALID_TOKEN = "Invalid Token";
  public static final String MESSAGE = "message";
  public static final String DOCUMENT = "document";
  public static final String EMAIL_WELCOME_TEMPLATE = "email/welcome";
  /**
   * Generic log Constants.
   */
  public static final String USERNAME_EXITS_MESSAGE
          = "The username already exits. Displaying warning to the user";
  public static final String EMAIL_EXISTS_MESSAGE
          = "The email already exits. Displaying warning to the user";
  public static final String ERROR_ASSIGNING_ROLES_AND_CREATING_USER
          = "Could not successfully assign roles and create user {}";
  public static final String USER_TOKEN_CREATED_SUCCESSFULLY
          = "User password token created Successfully as {}";
  public static final String USER_CREATED_SUCCESS_MESSAGE = "User created Successfully {}";
  /**
   * URL Mapping Constants.
   */
  public static final String SIGN_UP_URL_MAPPING = "/sign-up";
  public static final String SIGN_UP_VERIFY_URL_MAPPING = "/complete-sign-up";
  /**
   * View Name Constants.
   */
  public static final String SIGN_UP_VIEW_NAME = "user/sign-up";
  /**
   * Model Key Constants.
   */
  public static final String DUPLICATED_USERNAME_KEY = "duplicatedUsername";
  public static final String DUPLICATED_EMAIL_KEY = "duplicatedEmail";
  public static final String SIGN_UP_SUCCESS_KEY = "signedUp";
  public static final String SIGN_UP_PENDING_KEY = "verify";
  public static final String SIGN_UP_ERROR = "signUpError";
  /**
   * Message Key Constants.
   */
  public static final String CONFIRMATION_PENDING_EMAIL_SUBJECT
          = "signup.confirmation.pending.email.subject";
  public static final String CONFIRMATION_SUCCESS_EMAIL_SUBJECT
          = "signup.confirmation.success.email.subject";
  public static final String CONFIRMATION_SUCCESS_EMAIL_TEXT
          = "signup.confirmation.success.email.text";

  private SignUpControllerConstant() {
    throw new AssertionError("Non Instantiable");
  }
}
