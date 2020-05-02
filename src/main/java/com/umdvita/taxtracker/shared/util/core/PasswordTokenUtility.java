package com.umdvita.taxtracker.shared.util.core;

import com.umdvita.taxtracker.backend.persistence.domain.security.PasswordToken;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.constant.ApplicationConstant;
import com.umdvita.taxtracker.shared.dto.PasswordTokenDto;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.dto.mapper.PasswordTokenDtoMapper;
import com.umdvita.taxtracker.shared.dto.mapper.UserDtoMapper;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * This utility class holds all password token shared methods used in the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public abstract class PasswordTokenUtility {

  private PasswordTokenUtility() {
    throw new AssertionError(ApplicationConstant.ASSERTION_ERROR_MESSAGE);
  }

  /**
   * Transfers data from entity to returnable object.
   *
   * @param passwordToken stored user details
   * @return user dto
   */
  public static PasswordTokenDto getPasswordTokenDto(PasswordToken passwordToken) {
    InputValidationUtility.validateInputs(passwordToken);
    return PasswordTokenDtoMapper.MAPPER.toPasswordTokenDto(passwordToken);
  }
}
