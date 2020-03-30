package com.umdvita.taxtracker.backend.service.security;

import com.umdvita.taxtracker.backend.persistence.domain.security.PasswordToken;
import com.umdvita.taxtracker.shared.dto.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * Password token service to provide implementation for the definitions.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
public interface PasswordTokenService {

  /**
   * Save token with user specified.
   *
   * @param token   the token
   * @param userDto the user
   * @return created token assigned to the user
   */
  Optional<PasswordToken> savePasswordTokenWithUser(String token, UserDto userDto);

  /**
   * Save token with user specified.
   *
   * @param token   the token
   * @param userDto the user
   * @param period  the expiry duration for this token
   * @return created token assigned to the user
   */
  Optional<PasswordToken> savePasswordTokenWithUser(String token, UserDto userDto, int period);

  /**
   * Gets all tokens.
   *
   * @return created token assigned to the user
   */
  List<PasswordToken> getPasswordTokens();

  /**
   * Gets token with token specified.
   *
   * @param token the token
   * @return created token assigned to the user
   */
  Optional<PasswordToken> getPasswordTokenByToken(String token);

  /**
   * Gets token with id specified.
   *
   * @param id the id
   * @return created token assigned to the user
   */
  Optional<PasswordToken> getPasswordTokenById(Long id);

  /**
   * Ensure that the provided userId matches with the user associated with the token.
   *
   * @param token the token
   * @param userId        the userId
   * @return null if no match
   */
  PasswordToken validateUserToken(String token, String userId);

  /**
   * Update token.
   *
   * @param passwordToken the token to update
   * @return passwordToken the updated passwordToken
   */
  PasswordToken updateToken(PasswordToken passwordToken);

  /**
   * Delete token.
   *
   * @param passwordToken the token to delete
   */
  void deletePasswordToken(PasswordToken passwordToken);

  /**
   * Deletes all tokens associated with the user.
   *
   * @param userDto the user
   */
  void deleteByUser(UserDto userDto);

  /**
   * Deletes all tokens associated with the user email.
   *
   * @param email the email
   */
  void deleteByUser(String email);

  /**
   * Deletes all tokens.
   */
  void deleteAll();

  /**
   * Creates a new Password Reset Token for the user identified by the given
   * email.
   *
   * @param email The email uniquely identifying the user
   * @return PasswordToken a new Password Reset Token for the user
   *         identified by the given email or null if none is found
   */
  Optional<PasswordToken> createTokenForEmail(String email);
}
