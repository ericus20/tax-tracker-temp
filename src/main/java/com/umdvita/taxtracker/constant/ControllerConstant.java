package com.umdvita.taxtracker.constant;

/**
 * This class holds all constants used by the Controller.
 *
 * @author Eric Opoku
 */
public abstract class ControllerConstant {

  public static final int MAXIMUM_SESSIONS = 200;

  /**
   * URL Mapping Constants.
   */
  public static final String INDEX_URL_MAPPING = "/";
  public static final String LOGIN_URL_MAPPING = "/login";
  public static final String FEATURES_URL_MAPPING = "/features";
  public static final String WHAT_TO_BRING_URL_MAPPING = "/what-to-bring";
  public static final String MORE_ON_TAXES_URL_MAPPING = "/more-on-taxes";

  public static final String SIGN_UP_URL_MAPPING = "/sign-up";
  public static final String FORGOT_ROOT_MAPPING = "/user/forgot/";
  public static final String SIGN_UP_VERIFY_URL_MAPPING = "/complete-sign-up";

  public static final String ABOUT_US_URL = "/about-us";
  public static final String COPY_ROOT_URL = "/copy";
  public static final String PRIVACY_POLICY_URL = "/privacy-policy";
  public static final String TERMS_AND_CONDITIONS_URL = "/terms-and-conditions";

  /**
   * View Name Constants.
   */
  public static final String INDEX_VIEW_NAME = "index";
  public static final String FEATURES_VIEW_NAME = "features";
  public static final String WHAT_TO_BRING_VIEW_NAME = "what-to-bring";
  public static final String MORE_ON_TAXES_VIEW_NAME = "more-on-taxes";
  public static final String LOGIN_VIEW_NAME = "user/login";
  public static final String EXPIRED_VIEW_NAME = "expired";

  public static final String ABOUT_US_VIEW_NAME = "copy/about-us";
  public static final String PRIVACY_VIEW_NAME = "copy/privacy-policy";
  public static final String TERMS_AND_CONDITION_VIEW_NAME = "copy/terms-and-conditions";

  /**
   * URL Redirection Constants.
   */
  public static final String REDIRECT_TO_TAX_DASHBOARD = "redirect:/tax/dashboard";
  public static final String REDIRECT_HOME = "redirect:/";
  public static final String REDIRECT_TO_LOGIN = "redirect:/login";

  /**
   * Model Key Constant for email success.
   */
  public static final String USER_REQUEST_MODEL_KEY_NAME = "userRequestModel";

  /**
   * Security Controller URI Mappings.
   */
  public static final String LOGIN_URL = "/login/**";
  public static final String ADMIN = "/admin/**";
  public static final String ACTUATOR = "/actuator/**";
  public static final String LEADER = "/leader/**";
  public static final String VOLUNTEER = "/volunteer";
  public static final String LOGIN_LOGOUT = "/login?logout";
  public static final String LOGOUT = "/logout";
  public static final String REMEMBER_ME = "remember-me";
  public static final String JSESSIONID = "JSESSIONID";
  public static final String SET_COOKIE = "SET-COOKIE";
  public static final String WEBJARS = "/webjars/**";
  public static final String CSS = "/css/**";
  public static final String JS = "/js/**";
  public static final String VENDOR = "/vendor/**";
  public static final String VENDOR_DIST = "/vendor/dist/**";
  public static final String FONTS = "/fonts/**";
  public static final String STATIC = "/static/**";
  public static final String RESOURCES = "/resources/**";
  public static final String IMAGES = "/images/**";
  public static final String FAVICON = "/favicon/**";
  public static final String COPY = "/copy/**";
  public static final String CONTACT = "/contact/**";
  public static final String FEATURES = "/features/**";
  public static final String WHAT_TO_BRING = "/what-to-bring/**";
  public static final String MORE_ON_TAXES = "/more-on-taxes/**";
  public static final String CONSOLE = "/console/**";
  public static final String SIGN_UP = "/sign-up/**";
  public static final String EXPIRED_URL = "/expired-url";
  public static final String ROOT = "/";
  public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";

  private ControllerConstant() {
  }
}
