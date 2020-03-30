package com.umdvita.taxtracker.constant.security;

public abstract class SecurityConfigConstant {

  public static final int STRENGTH = 12;
  public static final int MAXIMUM_SESSIONS = 200;

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

  private SecurityConfigConstant() {
    throw new AssertionError("Non Instantiable");
  }

}
