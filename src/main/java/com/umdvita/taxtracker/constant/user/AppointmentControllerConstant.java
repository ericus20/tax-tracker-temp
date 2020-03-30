package com.umdvita.taxtracker.constant.user;

/**
 * Constants used in the Appointment Controller of the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public abstract class AppointmentControllerConstant {

  /**
   * Url Mappings.
   */
  public static final String APPOINTMENT_URL_MAPPING = "/appointment";
  public static final String APPOINTMENT_USER_URL_MAPPING = "/appointment/user";
  public static final String APPOINTMENT_RESOURCE_URL_MAPPING = "/rest/v1/appointments";

  /**
   * Model keys.
   */
  public static final int HOUR = 0;
  public static final int MINUTE = 1;
  public static final String SUCCESS = "success";
  public static final String APPOINTMENT_DTO = "appointmentDto";
  public static final String APPOINTMENT_UPDATE_REDIRECT = "appointmentUpdateRedirect";
  public static final String APPOINTMENT_UPDATE_REMINDER = "appointmentUpdateReminder";
  public static final String ERROR = "error";
  public static final String ERROR_MESSAGE = "errorMessage";
  public static final String DELETE_SUCCESS = "deleteSuccess";
  public static final String TIME_SLOTS = "timeSlots";
  public static final String APPOINTMENTS = "appointments";

  /**
   * Redirect URL.
   */
  public static final String REDIRECT_TO_APPOINTMENT_USER = "redirect:/appointment/user";

  /**
   * View Name.
   */
  public static final String TAX_MANAGE_APPOINTMENT_VIEW_NAME = "tax/manage-appointment";
  public static final String APPOINTMENT = "tax/appointment";


  private AppointmentControllerConstant() {
    throw new AssertionError("Non Instantiable");
  }
}
