package com.umdvita.taxtracker.web.model.feedback;

import com.umdvita.taxtracker.shared.dto.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The Email format pojo used to conveniently transport email details.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"files", "recipients"})
public class EmailFormat {
  private UserDto receiver;
  private UserDto sender;
  private String subject;
  private String template;
  private Context context;
  private String message;
  private String to;
  private String from;
  private String link;
  private List<String> recipients;
  private Map<String, String> urls;
  private Set<File> files;
}
