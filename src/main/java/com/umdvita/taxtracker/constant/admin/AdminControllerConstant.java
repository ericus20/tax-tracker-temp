package com.umdvita.taxtracker.constant.admin;

/**
 * This class holds all constants used by the operations available to the ADMIN.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public abstract class AdminControllerConstant {

  /**
   * Admin Controller URI Mappings.
   */
  public static final String ADMIN_ROOT_URL = "/admin";
  public static final String USER_ROOT_URL = "/admin/users";
  public static final String GROUP_ROOT_URL = "/admin/groups";
  public static final String ADMIN_APPOINTMENT_ROOT_URL = "/admin/appointment";
  public static final String LEADER_APPOINTMENT_ROOT_URL = "/leader/appointment";

  /**
   * Admin Controller View Names.
   */
  public static final String ADMIN_APPOINTMENT_CALENDAR_VIEW_NAME = "tax/admin/calendar";
  public static final String ADMIN_APPOINTMENT_VIEW_NAME = "tax/admin/all-appointments";
  public static final String ADMIN_USER_VIEW_NAME = "tax/admin/manage-users";
  public static final String ADMIN_GROUP_VIEW_NAME = "tax/admin/manage-groups";
  public static final String ADMIN_MANAGE_GROUP_VIEW_NAME = "tax/admin/manage-group-members";

  private AdminControllerConstant() {
    throw new AssertionError("Non Instantiable");
  }
}
