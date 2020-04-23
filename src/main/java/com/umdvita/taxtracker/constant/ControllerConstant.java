package com.umdvita.taxtracker.constant;

/**
 * This class holds all constants used by the Controller.
 *
 * @author Eric Opoku
 */
public abstract class ControllerConstant {


  /**
   * URL Mapping Constants.
   */
  public static final String INDEX_URL_MAPPING = "/";
  public static final String LOGIN_URL_MAPPING = "/login";
  public static final String FEATURES_URL_MAPPING = "/features";
  public static final String WHAT_TO_BRING_URL_MAPPING = "/what-to-bring";
  public static final String MORE_ON_TAXES_URL_MAPPING = "/more-on-taxes";

  /**
   * View Name Constants.
   */
  public static final String INDEX_VIEW_NAME = "index";
  public static final String FEATURES_VIEW_NAME = "features";
  public static final String WHAT_TO_BRING_VIEW_NAME = "what-to-bring";
  public static final String MORE_ON_TAXES_VIEW_NAME = "more-on-taxes";
  public static final String LOGIN_VIEW_NAME = "user/login";
  public static final String EXPIRED_VIEW_NAME = "expired";

  /**
   * URL Redirection Constants.
   */
  public static final String REDIRECT_TO_TAX_DASHBOARD = "redirect:/tax/dashboard";
  public static final String REDIRECT_HOME = "redirect:/";
  public static final String REDIRECT_TO_LOGIN = "redirect:/login";

  /**
   * Copy Controller URI Mappings.
   */
  public static final String ABOUT_US_URL = "/about-us";
  public static final String COPY_ROOT_URL = "/copy";
  public static final String PRIVACY_POLICY_URL = "/privacy-policy";
  public static final String TERMS_AND_CONDITIONS_URL = "/terms-and-conditions";

  /**
   * Copy Controller View Names.
   */
  public static final String ABOUT_US_VIEW_NAME = "copy/about-us";
  public static final String PRIVACY_VIEW_NAME = "copy/privacy-policy";
  public static final String TERMS_AND_CONDITION_VIEW_NAME = "copy/terms-and-conditions";

  private ControllerConstant() {
  }
}
