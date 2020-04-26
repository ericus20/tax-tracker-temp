package com.umdvita.taxtracker.backend.service.mail.impl;

import com.umdvita.taxtracker.backend.service.mail.EmailService;
import com.umdvita.taxtracker.web.model.feedback.EmailFormat;
import com.umdvita.taxtracker.web.model.feedback.Feedback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * This abstract class leverages the required methods by the EmailService.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public abstract class AbstractEmailServiceImpl implements EmailService {

  /**
   * Sends an email given a feedback Pojo.
   *
   * @param feedback the feedback pojo.
   * @see Feedback
   */
  @Override
  public void sendMailWithFeedback(final Feedback feedback) {
    sendMail(prepareSimpleMailMessage(feedback));
  }

  /**
   * Prepares a simple mail message object with the feedback pojo details.
   *
   * @param feedback the feedback pojo.
   * @return the simple mail message.
   */
  private SimpleMailMessage prepareSimpleMailMessage(final Feedback feedback) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setSubject(feedback.getSubject());
    simpleMailMessage.setTo(feedback.getTo());
    simpleMailMessage.setSentDate(new Date());
    simpleMailMessage.setText(feedback.getMessage());
    simpleMailMessage.setReplyTo(feedback.getFrom());
    simpleMailMessage.setCc(feedback.getRecipients().toArray(new String[0]));

    // prepare and update the simpleMailMessage with senders name.
    configureInternetAddress(feedback, simpleMailMessage);
    return simpleMailMessage;
  }

  /**
   * Masks the email address with the senders name.
   *
   * @param feedback          the feedback
   * @param simpleMailMessage the simpleMailMessage
   */
  private void configureInternetAddress(Feedback feedback, SimpleMailMessage simpleMailMessage) {
    try {
      InternetAddress address = new InternetAddress(feedback.getEmail(), feedback.getName());
      simpleMailMessage.setFrom(String.valueOf(address));
    } catch (UnsupportedEncodingException e) {
      LOG.error("Could not create an internet address for the feedback {}", feedback, e);
    }
  }

  /**
   * Embed all files in the emailFormat as attachments in the email.
   *
   * @param emailFormat the emailFormat
   * @param mimeMessageHelper the message helper
   */
  void addAttachments(EmailFormat emailFormat, MimeMessageHelper mimeMessageHelper) {
    emailFormat.getFiles().forEach(file -> addAttachment(file, mimeMessageHelper));
  }

  /**
   * Embed an attachment to an email.
   *
   * @param file the file
   * @param mimeMessageHelper the message helper
   */
  private void addAttachment(File file, MimeMessageHelper mimeMessageHelper) {
    String fileName = file.getName();
    try {
      if (!file.exists()) {
        LOG.error("File does not exist: {}", file);
      }
      mimeMessageHelper.addAttachment(fileName, file);
      LOG.debug("Added a file attachment: {}", fileName);
    } catch (MessagingException ex) {
      LOG.error("Failed to add a file attachment: {}", fileName, ex);
    }
  }
}
