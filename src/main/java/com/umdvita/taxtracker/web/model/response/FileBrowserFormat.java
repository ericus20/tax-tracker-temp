package com.umdvita.taxtracker.web.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * This class models the format of the tax file browser response produced in the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@EqualsAndHashCode(of = "text")
public final class FileBrowserFormat implements Serializable {
  private static final long serialVersionUID = 1970972247072391051L;

  private final String text;
  private String href;
  private int tags;
  private List<FileBrowserFormat> nodes;
}
