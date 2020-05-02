package com.umdvita.taxtracker.constant;

/**
 * User constant provides details about user.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public abstract class UserConstant {

  /**
   * URL Mapping Constants.
   */
  public static final String ERROR_CREATING_USER = "Error creating user";


  /**
   * Model Keys.
   */
  public static final String USER_ID = "userId";
  public static final String USERNAME = "username";
  public static final String USER_MODEL_KEY = "user";
  public static final String PROFILE_IMAGE = "profileImage";
  public static final String USER_DTOS = "userDtos";
  public static final String ADMIN_DTOS = "adminDtos";
  public static final String TAX_PREPARER_DTOS = "taxPreparerDtos";
  public static final String LEADER_DTOS = "leaderDtos";
  public static final String CUSTOMER_DTOS = "customerDtos";
  public static final String USERS = "users";
  public static final String GROUPS = "groups";

  /**
   * User Detail constants.
   */
  public static final String ADMIN_USERNAME = "admin";
  public static final String TEST_USERNAME = "test";
  public static final String TEST_2_USERNAME = "test2";
  public static final String TEST_3_USERNAME = "test3";

  /**
   * Model messages.
   */
  public static final String USER_DELETE_MESSAGE = "User successfully deleted";
  public static final String PASSWORD_TOKEN_SUCCESS = "Successfully created token {} for user {}";
  public static final String USER_NOT_FOUND_BY_EMAIL_LOG = "User with email {} is not found in our records";
  public static final String USER_NOT_FOUND_BY_EMAIL = "No user found with the specified email";
  public static final String USER_NOT_FOUND_BY_USERNAME = "No user found with the specified username";

  private UserConstant() {
    throw new AssertionError("Non Instantiable");
  }
}
