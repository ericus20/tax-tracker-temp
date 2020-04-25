package com.umdvita.taxtracker.config.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration properties are initialized in this configuration class.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mslc.application")
public class ApplicationProperties {

  private String baseUrl;

  // Application default account credentials
  private String webmasterEmail;
  private String webmasterUsername;
  private String webmasterPassword;
  private String testUserPassword;
  private String storageDirectory;

  // AWS credentials
  private String awsS3Region;
  private String awsAccessKeyId;
  private String awsSecretAccessKey;
  private String awsS3BucketName;

  // Security props used in encryption and encoding
  private String salt;
  private String encryptionKey;
  private String encryptionIntVec;
}