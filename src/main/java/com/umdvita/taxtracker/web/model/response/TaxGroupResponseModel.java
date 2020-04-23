package com.umdvita.taxtracker.web.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class models the format of the response produced in the controller endpoints.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class TaxGroupResponseModel {
  private Long id;
  private String name;
  private String leaderName;
  private String leaderEmail;
  private int preparers;
}
