package com.umdvita.taxtracker.web.model.feedback;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * The feedback pojo used as a template for user feedback journey.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Slf4j
@ToString
@EqualsAndHashCode
public class Feedback {

  private String name;
  private String email;
  private String subject;
  private String to;
  private String from;
  private String phone;
  private String message;
  private Long taxReturnId;
  private List<String> recipients = new ArrayList<>();

}
