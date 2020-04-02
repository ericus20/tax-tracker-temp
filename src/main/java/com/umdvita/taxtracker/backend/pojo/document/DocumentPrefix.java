package com.umdvita.taxtracker.backend.pojo.document;

import lombok.Builder;
import lombok.Data;

/**
 * The document prefix pojo used to define the format for storing files.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
public final class DocumentPrefix {
  private final String base;
  private final String username;
  private final String type;
  private final String year;
  private final String version;

  @Override
  public String toString() {
    String separator = "/";
    return base + separator + username + separator + type + separator + year + separator + version;
  }
}
