package com.umdvita.taxtracker.config.core;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

/**
 * The ApplicationConfig class provides beans needed for all environments.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableScheduling
public class ApplicationConfig {

  private static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE"};

  @Value("${spring.mail.username}")
  private String mailUsername;
  @Value("${spring.mail.password}")
  private String mailPassword;

  /**
   * A bean to be used by AmazonS3 Service.
   *
   * @param props the application properties
   * @return instance of AmazonS3Client
   */
  @Bean
  public AmazonS3 amazonS3(ApplicationProperties props) {

    AWSCredentials credentials = new BasicAWSCredentials(props.getAwsAccessKeyId(), props.getAwsSecretAccessKey());
    return AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName(props.getAwsS3Region()))
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
  }

  /**
   * A bean for CORS allowing specified methods.
   * This configures to include headers for Cross-Origin Resource Sharing (CORS) in the response
   *
   * @return web mvc configurer.
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {

      @Override
      public void addCorsMappings(final @NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(ALLOWED_METHODS);
      }
    };
  }

  /**
   * Sub-interface of the above MailSender.
   * It supports MIME messages and is mostly used in conjunction with
   * the MimeMessageHelper class for the creation of a MimeMessage. It's recommended to use the
   * MimeMessagePreparator mechanism with this interface
   *
   * @return javaMailSender
   */
  @Bean
  public JavaMailSender mailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setUsername(mailUsername);
    mailSender.setPassword(mailPassword);
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.fallback", "true");
    props.put("mail.smtp.ssl.checkserveridentity", "true");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.ssl.enable", "true");
    //    props.put("mail.debug", "true");
    mailSender.setJavaMailProperties(props);
    return mailSender;
  }

}


