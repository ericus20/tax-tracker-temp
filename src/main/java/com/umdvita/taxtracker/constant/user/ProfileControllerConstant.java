package com.umdvita.taxtracker.constant.user;

/**
 * Created by Eric on 3/30/2018.
 *
 * @author Eric Opoku
 */
public abstract class ProfileControllerConstant {

  /**
   * URL Mapping Constants.
   */
  public static final String USER_PROFILE_URL_MAPPING = "/profile";
  public static final String USER_PROFILE_UPDATE_URL_MAPPING = "/update";

  /**
   * URL Redirect Mapping Constants.
   */
  public static final String REDIRECT_TO_LOGIN = "redirect:/login";
  public static final String REDIRECT_TO_PROFILE = "redirect:/profile";
  /**
   * View Name Constants.
   */
  public static final String USER_PROFILE_VIEW_NAME = "tax/profile";

  /**
   * Profile Model Keys.
   */
  public static final String PIC_SUM_PHOTOS_150_RANDOM = "https://picsum.photos/150/150/?random";
  public static final String NEW_PROFILE = "newProfile";
  public static final String DEFAULT = "default";
  public static final String UPDATE_MODE = "updateMode";
  public static final String UPDATE_SUCCESS = "updateSuccess";
  public static final String ERROR = "error";

  private ProfileControllerConstant() {
    throw new AssertionError("Non Instantiable");
  }
}
