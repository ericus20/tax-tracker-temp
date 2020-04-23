package com.umdvita.taxtracker.web.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class models the format of the request in the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class PreparerRequestModel {
  private String groupName;
  private String username;
  private String email;
  private boolean leader;
}
