package com.umdvita.taxtracker.constant.amazon;

/**
 * This class holds all constants used by the AmazonS3 Service.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public abstract class AmazonS3Constant {

  public static final String S3_BASE_URL = "https://mslc-vita.s3.amazonaws.com";
  private static final String S3_EMAIL_IMAGE_BASE_URL = S3_BASE_URL + "/email-template-images";
  public static final String S3_EMAIL_IMG_BG_URL = S3_EMAIL_IMAGE_BASE_URL + "/email-bg.jpg";
  public static final String S3_EMAIL_IMG_UPDATE_URL = S3_EMAIL_IMAGE_BASE_URL + "/update.jpg";

  private AmazonS3Constant() {
    throw new AssertionError("Non instantiable");
  }


}
