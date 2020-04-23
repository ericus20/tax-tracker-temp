package com.umdvita.taxtracker.web.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * This class models the format of the tax file browser response produced in the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(of = "text")
public class FileBrowserResponse {
  private String text;
  private String href;
  private List<FileBrowserFormat> nodes;
}
