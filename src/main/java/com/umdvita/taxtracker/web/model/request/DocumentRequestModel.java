package com.umdvita.taxtracker.web.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class models the format of the admin request allowed through the controller endpoints for group.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class DocumentRequestModel {
  private String year;
  private String email;
  private String signed;
  private String fileType;
  private MultipartFile file;

  public DocumentRequestModel(String email, String signed) {
    this.email = email;
    this.signed = signed;
  }

  public DocumentRequestModel(String signed) {
    this(null, signed);
  }
}
