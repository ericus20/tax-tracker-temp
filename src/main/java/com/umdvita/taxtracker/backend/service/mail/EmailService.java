package com.umdvita.taxtracker.backend.service.mail;

import com.umdvita.taxtracker.web.model.feedback.EmailFormat;
import com.umdvita.taxtracker.web.model.feedback.Feedback;
import org.springframework.mail.SimpleMailMessage;

/**
 * Provides operations on sending emails within the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public interface EmailService {

  /**
   * Sends an email with the provided simple mail message object.
   *
   * @param simpleMailMessage the simple mail message.
   */
  void sendMail(SimpleMailMessage simpleMailMessage);

  /**
   * Sends an email with the provided EmailFormat details.
   *
   * @param emailFormat the email format
   * @see EmailFormat
   */
  void sendHtmlEmail(EmailFormat emailFormat);

  /**
   * Sends an email given a feedback Pojo.
   *
   * @param feedback the feedback pojo.
   * @see Feedback
   */
  void sendMailWithFeedback(Feedback feedback);

  /**
   * Sends an email with the provided details and template for html with an attachment.
   *
   * @param emailFormat the email format
   */
  void sendHtmlEmailWithAttachment(EmailFormat emailFormat);
}
