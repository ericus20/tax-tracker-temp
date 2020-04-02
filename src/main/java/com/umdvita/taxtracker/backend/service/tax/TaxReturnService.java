package com.umdvita.taxtracker.backend.service.tax;

import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.backend.persistence.domain.tax.TaxReturn;
import com.umdvita.taxtracker.shared.dto.UserDto;

/**
 * The TaxReturnService provides operations allowed on a taxReturn object.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public interface TaxReturnService {

  /**
   * Starts a new taxReturn object.
   *
   * @param taxReturn the taxReturn
   * @return created taxReturn
   */
  TaxReturn startTaxReturn(TaxReturn taxReturn);

  /**
   * Starts a new taxReturn for the year and user provided.
   *
   * @param year    the tax year
   * @param userDto the user
   * @return created taxReturn
   */
  TaxReturn startTaxReturn(String year, UserDto userDto);

  /**
   * Checks if a taxReturn exists with the user and year specified.
   *
   * @param user the user
   * @param year the year
   * @return <code>true</code> if exists
   */
  boolean existsByUserAndYear(User user, String year);
}
