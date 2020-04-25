package com.umdvita.taxtracker.shared.util.validation;

import com.umdvita.taxtracker.shared.dto.UserDto;
import org.thymeleaf.util.StringUtils;

import java.util.Objects;

public interface UserValidationUtility {

  static boolean isRequiredFieldsValid(UserDto userDto) {
    return Objects.nonNull(userDto)
            && !StringUtils.isEmptyOrWhitespace(userDto.getFirstName())
            && !StringUtils.isEmptyOrWhitespace(userDto.getLastName())
            && !StringUtils.isEmptyOrWhitespace(userDto.getUsername())
            && !StringUtils.isEmptyOrWhitespace(userDto.getEmail())
            && !StringUtils.isEmptyOrWhitespace(userDto.getName())
            && !StringUtils.isEmptyOrWhitespace(userDto.getDob())
            && !StringUtils.isEmptyOrWhitespace(userDto.getPhone());
  }
}
