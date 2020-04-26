package com.umdvita.taxtracker.backend.service.mail.impl;

import com.umdvita.taxtracker.config.core.ApplicationProperties;
import com.umdvita.taxtracker.constant.ProfileType;
import com.umdvita.taxtracker.constant.SignUpControllerConstant;
import com.umdvita.taxtracker.exception.tax.TaxTrackerException;
import com.umdvita.taxtracker.web.model.feedback.EmailFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * SmtpEmailServiceImpl Class has the operation of email sending in a real time.
 *
 * @author Eric Opoku
 * @version 1.0
 * @see com.umdvita.taxtracker.backend.service.mail.EmailService
 * @since 1.0
 */
@Slf4j
@Service
@Profile(value = {ProfileType.PROD, ProfileType.DOCKER, ProfileType.PROD_AWS})
public class SmtpEmailServiceImpl extends AbstractEmailServiceImpl {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private ApplicationProperties applicationProperties;

  /**
   * Sends an email with the provided simple mail message object.
   *
   * @param simpleMailMessage the simple mail message.
   */
  @Override
  public void sendMail(final SimpleMailMessage simpleMailMessage) {
    try {
      LOG.debug("Sending mail with content {}", simpleMailMessage);
      if (Objects.isNull(simpleMailMessage.getFrom())) {
        simpleMailMessage.setFrom(applicationProperties.getWebmasterEmail());
      }
      mailSender.send(simpleMailMessage);
      LOG.debug(SignUpControllerConstant.MAIL_SUCCESS_MESSAGE);
    } catch (Exception e) {
      LOG.error("Error sending generic mail with content as {}", simpleMailMessage);
      throw new TaxTrackerException(e);
    }
  }

  /**
   * Sends an email with the provided EmailFormat details.
   *
   * @param emailFormat the email format
   * @see EmailFormat
   */
  @Override
  public void sendHtmlEmail(EmailFormat emailFormat) {
    LOG.debug("Sending html email with details {}", emailFormat);
    try {
      mailSender.send(getMimeMessage(emailFormat, Objects.nonNull(emailFormat.getFiles())));
      LOG.debug(SignUpControllerConstant.MAIL_SUCCESS_MESSAGE);
    } catch (MessagingException | UnsupportedEncodingException e) {
      LOG.error("Unexpected exception sending an html email", e);
    }
  }

  /**
   * Sends an email with the provided details and template for html with an attachment.
   *
   * @param emailFormat the email format;
   */
  @Override
  public void sendHtmlEmailWithAttachment(EmailFormat emailFormat) {
    try {
      mailSender.send(getMimeMessage(emailFormat, true));
    } catch (MessagingException | UnsupportedEncodingException e) {
      LOG.error("Unexpected exception sending an html email", e);
    }
  }


  /**
   * Prepares a MimeMessage with provided EmailFormat.
   *
   * @param emailFormat    the emailFormat
   * @param withAttachment true if attachment is required
   * @return MimeMessage the MimeMessage
   */
  private MimeMessage getMimeMessage(EmailFormat emailFormat, boolean withAttachment) throws MessagingException,
          UnsupportedEncodingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, UTF_8.name());
    mimeMessageHelper.setTo(emailFormat.getTo());
    mimeMessageHelper.setSentDate(new Date());
    if (Objects.nonNull(emailFormat.getRecipients()) && !emailFormat.getRecipients().isEmpty()) {
      mimeMessageHelper.setCc(emailFormat.getRecipients().toArray(String[]::new));
    }
    String body = templateEngine.process(emailFormat.getTemplate(), emailFormat.getContext());
    mimeMessageHelper.setText(body, true);
    mimeMessageHelper.setSubject(emailFormat.getSubject());
    // TODO: 4/26/2020 setup the senders address with the given name

    if (withAttachment && Objects.nonNull(emailFormat.getFiles())) {
      addAttachments(emailFormat, mimeMessageHelper);
    }

    return mimeMessage;
  }
}
