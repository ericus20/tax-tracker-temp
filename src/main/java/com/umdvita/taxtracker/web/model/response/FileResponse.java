package com.umdvita.taxtracker.web.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class models the format of the file response produced in the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class FileResponse {
  private String name;
  private String actualName;
  private String uri;

  public FileResponse(String name, String actualName, String uri) {
    this.name = name;
    this.actualName = actualName.split("\\.")[0];
    this.uri = uri;
  }
}
