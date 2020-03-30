package com.umdvita.taxtracker.constant.tax;

/**
 * Constants used in the Appointment Controller of the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public abstract class TaxReturnControllerConstant {

  /**
   * Url Mappings.
   */
  public static final String TAX_ROOT_MAPPING = "/tax";
  public static final String TAX_START_URL_MAPPING = "/start";
  public static final String TAX_STATUS_URL_MAPPING = "/status";
  public static final String TAX_STATUS_BY_YEAR_URL_MAPPING = "/status/year";
  public static final String TAX_START_FOR_CLIENT_URL_MAPPING = "/start/client";
  public static final String TAX_DASHBOARD_URL_MAPPING = "/tax/dashboard";
  public static final String TAX_PREPARER_ROOT_MAPPING = "/tax/preparer";
  public static final String DASHBOARD_URL_MAPPING = "/dashboard";

  /**
   * Tax Model Keys.
   */
  public static final String TAX_START_UPDATE_REMINDER = "taxStartUpdateReminder";
  public static final String TAX_START_UPDATE_REDIRECT = "taxStartUpdateRedirect";
  public static final String TAX_RETURN = "taxReturn";
  public static final String TAX_RETURN_DTO = "taxReturnDto";
  public static final String TAX_PREPARER_NAME = "preparerName";
  public static final String TAX_STATUS_INDEX = "index";
  public static final String TAX_OWNER = "taxOwner";
  public static final String TAX_SIGNED = "signed";
  public static final String ERROR = "error";
  public static final String SUCCESS = "success";
  public static final String YEAR = "year";

  /**
   * View Names.
   **/
  public static final String TAX_DASHBOARD_VIEW_NAME = "tax/dashboard";
  public static final String TAX_STATUS_BY_YEAR_VIEW_NAME = "tax/status-by-year";
  public static final String TAX_STATUS_VIEW_NAME = "tax/status";
  public static final String TAX_START_VIEW_NAME = "tax/start";
  public static final String TAX_START_FOR_CLIENT_VIEW_NAME = "tax/start-for-client";

  private TaxReturnControllerConstant() {
    throw new AssertionError("Non Instantiable");
  }
}
