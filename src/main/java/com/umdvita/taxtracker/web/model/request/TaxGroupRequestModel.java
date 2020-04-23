package com.umdvita.taxtracker.web.model.request;

import lombok.Data;

/**
 * This class models the format of the admin request allowed through the controller endpoints for group.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
public class TaxGroupRequestModel {
  private Long id;
  private String name;
  private String leaderEmail;
}
