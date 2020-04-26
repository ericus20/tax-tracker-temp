package com.umdvita.taxtracker.backend.service.mail.impl;

import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.constant.SignUpControllerConstant;
import com.umdvita.taxtracker.web.model.feedback.EmailFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Class simulates the operation of email sending without a real time call.
 *
 * @author Eric Opoku
 */
@Slf4j
@Service
@Profile(value = {ProfileType.TEST, ProfileType.DEV})
public class MockEmailServiceImpl extends AbstractEmailServiceImpl {

  private static final String SIMULATING_SENDING_AN_EMAIL = "Simulating sending an email...";

  /**
   * Sends an email with the provided simple mail message object.
   *
   * @param simpleMailMessage the simple mail message.
   */
  @Override
  public void sendMail(final SimpleMailMessage simpleMailMessage) {
    LOG.info(SIMULATING_SENDING_AN_EMAIL);
    LOG.info("Simple Mail Message content is {}", simpleMailMessage);
    LOG.info(SignUpControllerConstant.MAIL_SUCCESS_MESSAGE);
  }

  /**
   * Sends an email with the provided EmailFormat details.
   *
   * @param emailFormat the email format
   * @see EmailFormat
   */
  @Override
  public void sendHtmlEmail(EmailFormat emailFormat) {
    LOG.info(SIMULATING_SENDING_AN_EMAIL);
    LOG.info("Email format details include: {}", emailFormat);
    LOG.info(SignUpControllerConstant.MAIL_SUCCESS_MESSAGE);
  }

  /**
   * Sends an email with the provided details and template for html with an attachment.
   *
   * @param emailFormat the email format
   */
  @Override
  public void sendHtmlEmailWithAttachment(EmailFormat emailFormat) {
    LOG.info(SIMULATING_SENDING_AN_EMAIL);
    LOG.info("attachments to be emailed are {}", emailFormat.getFiles());
    LOG.info(SignUpControllerConstant.MAIL_SUCCESS_MESSAGE);
  }
}
