package com.umdvita.taxtracker.constant;

/**
 * Constants used in the UserUtilityConstant of the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public abstract class SiteConstant {

  public static final String MAIN_INTAKE_SITE_NAME = "UMD - VITA";
  public static final String SIDN = "S15011116";
  public static final String SITE_COORDINATOR = "Samuel Handwerger";
  public static final String SITE_CONTACT = "Samuel Handwerger";
  public static final String SITE_TELEPHONE = "(443) 386-3041";
  public static final String SITE_ADDRESS = ""
          + "7699 Mowatt Lane, College Park, 20742\n"
          + "Van Munching Hall\n";

  private SiteConstant() {
    throw new AssertionError("Non Instantiable");
  }
}
