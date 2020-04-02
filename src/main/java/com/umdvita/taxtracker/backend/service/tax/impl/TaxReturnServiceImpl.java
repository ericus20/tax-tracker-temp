package com.umdvita.taxtracker.backend.service.tax.impl;

import com.umdvita.taxtracker.annotation.Loggable;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.backend.persistence.domain.tax.TaxReturn;
import com.umdvita.taxtracker.backend.persistence.repository.tax.TaxReturnRepository;
import com.umdvita.taxtracker.backend.service.tax.TaxReturnService;
import com.umdvita.taxtracker.exception.tax.TaxReturnExistsException;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The TaxReturnServiceImpl class is an implementation for the TaxReturnService Interface.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class TaxReturnServiceImpl implements TaxReturnService {

  private final TaxReturnRepository taxReturnRepository;

  @Autowired
  public TaxReturnServiceImpl(TaxReturnRepository taxReturnRepository) {
    this.taxReturnRepository = taxReturnRepository;
  }

  /**
   * Starts a new taxReturn object.
   *
   * @param taxReturn the taxReturn
   * @return created taxReturn
   */
  @Override
  @Loggable
  @Transactional
  public TaxReturn startTaxReturn(TaxReturn taxReturn) {
    InputValidationUtility.validateInputs(getClass(), taxReturn);

    if (taxReturnRepository.existsByUserAndYear(taxReturn.getUser(), taxReturn.getYear())) {
      throw new TaxReturnExistsException("Tax return already exists for the year " + taxReturn.getYear());
    }
    TaxReturn persistedTaxReturns = taxReturnRepository.save(taxReturn);
    LOG.debug("TaxReturn successfully saved to the database as {}", taxReturn);
    return null;
  }

  /**
   * Starts a new taxReturn for the year and user provided.
   *
   * @param year    the tax year
   * @param userDto the user
   * @return created taxReturn
   */
  @Override
  public TaxReturn startTaxReturn(String year, UserDto userDto) {
    return null;
  }

  /**
   * Checks if a taxReturn exists with the user and year specified.
   *
   * @param user the user
   * @param year the year
   * @return <code>true</code> if exists
   */
  @Override
  public boolean existsByUserAndYear(User user, String year) {
    InputValidationUtility.validateInputs(getClass(), user, year);
    return taxReturnRepository.existsByUserAndYear(user, year);
  }
}
