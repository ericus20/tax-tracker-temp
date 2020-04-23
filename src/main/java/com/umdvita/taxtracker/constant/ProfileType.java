package com.umdvita.taxtracker.constant;

public abstract class ProfileType {

  public static final String DEV = "dev";
  public static final String DOCKER = "docker";
  public static final String PROD = "prod";
  public static final String PROD_AWS = "prod-aws";
  public static final String TEST = "test";

  private ProfileType() {
    throw new AssertionError("Non Instantiable");
  }
}
