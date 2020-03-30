package com.umdvita.taxtracker.constant.copy;

public abstract class CopyControllerConstant {

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

  private CopyControllerConstant() {
    throw new AssertionError("Non instantiable");
  }
}
