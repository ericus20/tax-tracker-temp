package com.umdvita.taxtracker.constant.user;

/**
 * Constants used in the Contact Controller of the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public abstract class ContactControllerConstant {

  /**
   * Url Mappings.
   */
  public static final String CONTACT_URL_MAPPING = "/contact";

  /**
   * view names.
   */
  public static final String CONTACT_US_VIEW_NAME = "copy/contact";
  /**
   * The key which identifies the feedback payload in the Model.
   */
  public static final String FEEDBACK_MODEL_KEY = "feedback";
  public static final String FEEDBACK_SUCCESS_KEY = "feedbackSuccess";
  public static final String FEEDBACK_ERROR_KEY = "feedbackError";

  private ContactControllerConstant() {
    throw new AssertionError("Non Instantiable");
  }
}
