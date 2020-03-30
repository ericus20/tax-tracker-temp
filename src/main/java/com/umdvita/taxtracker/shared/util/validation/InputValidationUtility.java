package com.umdvita.taxtracker.shared.util.validation;

import org.springframework.util.StringUtils;

/**
 * Validation utility class that holds methods used across application for validations.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public interface InputValidationUtility {

  /**
   * A helper method which takes in multiple arguments and validate each
   * instance not being null.
   *
   * @param inputs instances to be validated
   * @param clazz  the class within which the exception is thrown
   * @throws IllegalArgumentException if any of the inputs is {@literal null}.
   */
  static void validateInputs(Class<?> clazz, final Object... inputs) {
    for (Object input : inputs) {
      if (input == null || (input.getClass() == String.class && StringUtils.isEmpty(input))) {
        throw new IllegalArgumentException("Null elements are not allowed in: " + clazz.getName());
      }
    }
  }

  /**
   * A helper method which takes in multiple arguments and validate each
   * instance not being null.
   *
   * @param inputs instances to be validated
   * @throws IllegalArgumentException if any of the inputs is {@literal null}.
   */
  static void validateInputs(final Object... inputs) {
    for (Object input : inputs) {
      if (input == null || (input.getClass() == String.class && StringUtils.isEmpty(input))) {
        throw new IllegalArgumentException("Null elements are not allowed");
      }
    }
  }

}
