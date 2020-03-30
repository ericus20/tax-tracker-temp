package com.umdvita.taxtracker.backend.service.security.impl;

import com.umdvita.taxtracker.backend.persistence.domain.security.PasswordToken;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.backend.persistence.repository.security.PasswordTokenRepository;
import com.umdvita.taxtracker.backend.persistence.repository.security.UserRepository;
import com.umdvita.taxtracker.backend.service.security.PasswordTokenService;
import com.umdvita.taxtracker.exception.UnAuthorizedActionException;
import com.umdvita.taxtracker.shared.dto.UserDto;
import com.umdvita.taxtracker.shared.util.core.UserUtility;
import com.umdvita.taxtracker.shared.util.validation.InputValidationUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * The PasswordTokenServiceImpl class is an implementation for the PasswordTokenService Interface.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class PasswordTokenServiceImpl implements PasswordTokenService {

  /**
   * The password Reset token repository.
   */
  private final PasswordTokenRepository passwordTokenRepository;
  private final UserRepository userRepository;

  /**
   * Constructor for password reset dependencies.
   *
   * @param passwordTokenRepository the password token repository
   * @param userRepository          the user repository
   */
  @Autowired
  public PasswordTokenServiceImpl(
          final PasswordTokenRepository passwordTokenRepository,
          final UserRepository userRepository) {
    this.passwordTokenRepository = passwordTokenRepository;
    this.userRepository = userRepository;
  }

  /**
   * Save token with user specified.
   *
   * @param token   the token
   * @param userDto the user
   * @return created token assigned to the user
   */
  @Override
  @Transactional
  public Optional<PasswordToken> savePasswordTokenWithUser(String token, UserDto userDto) {
    return savePasswordTokenWithUser(token, userDto, -1);
  }

  /**
   * Save token with user specified.
   *
   * @param token   the token
   * @param userDto the user
   * @param period  the expiry duration for this token
   * @return created token assigned to the user
   */
  @Override
  @Transactional
  public Optional<PasswordToken> savePasswordTokenWithUser(String token, UserDto userDto, int period) {
    InputValidationUtility.validateInputs(getClass(), token, userDto);

    User storedUser = userRepository.getOneByEmail(userDto.getEmail());
    PasswordToken passwordToken = new PasswordToken(token, storedUser, period);
    passwordToken = passwordTokenRepository.save(passwordToken);
    return Optional.of(passwordToken);
  }

  /**
   * Gets all tokens.
   *
   * @return created token assigned to the user
   */
  @Override
  public List<PasswordToken> getPasswordTokens() {
    return passwordTokenRepository.findAll();
  }

  /**
   * Gets token with token specified.
   *
   * @param token the token
   * @return created token assigned to the user
   */
  @Override
  public Optional<PasswordToken> getPasswordTokenByToken(String token) {
    InputValidationUtility.validateInputs(getClass(), token);
    return Optional.ofNullable(passwordTokenRepository.findByToken(token));
  }

  /**
   * Gets token with id specified.
   *
   * @param id the id
   * @return created token assigned to the user
   */
  @Override
  public Optional<PasswordToken> getPasswordTokenById(Long id) {
    InputValidationUtility.validateInputs(getClass(), id);
    return passwordTokenRepository.findById(id);
  }

  /**
   * Ensure that the provided userId matches with the user associated with the token.
   *
   * @param token the token
   * @param userId        the userId
   * @return <code>true</code> if there is a match
   */
  @Override
  @Transactional
  public PasswordToken validateUserToken(String token, String userId) {
    InputValidationUtility.validateInputs(getClass(), token, userId);
    PasswordToken passwordToken = passwordTokenRepository.getOneByToken(token);
    if (Objects.nonNull(passwordToken)) {
      User user = passwordToken.getUser();
      passwordTokenRepository.delete(passwordToken);
      if (Objects.nonNull(user) && user.getUserId().equals(userId)) {
        return passwordToken;
      }
      throw new UnAuthorizedActionException("Unauthorized access to password token");
    }
    return null;
  }

  @Override
  @Transactional
  public PasswordToken updateToken(PasswordToken passwordToken) {
    InputValidationUtility.validateInputs(getClass(), passwordToken);
    return passwordTokenRepository.save(passwordToken);
  }

  /**
   * Delete token.
   *
   * @param passwordToken the token to delete
   */
  @Override
  @Transactional
  public void deletePasswordToken(PasswordToken passwordToken) {
    InputValidationUtility.validateInputs(getClass(), passwordToken);
    passwordTokenRepository.delete(passwordToken);
  }

  /**
   * Deletes all token associated with the user.
   *
   * @param userDto the user
   */
  @Override
  @Transactional
  public void deleteByUser(UserDto userDto) {
    InputValidationUtility.validateInputs(getClass(), userDto);
    List<PasswordToken> passwordTokens = getPasswordTokens();
    for (PasswordToken passwordToken : passwordTokens) {
      if (passwordToken.getUser().equals(UserUtility.getUserFromDto(userDto))) {
        deletePasswordToken(passwordToken);
      }
    }
  }

  /**
   * Deletes all token associated with the user email.
   *
   * @param email the email
   */
  @Override
  @Transactional
  public void deleteByUser(String email) {
    InputValidationUtility.validateInputs(getClass(), email);
    User user = userRepository.findByEmail(email);
    if (Objects.nonNull(user)) {
      UserDto userDto = UserUtility.getUserDto(user);
      deleteByUser(userDto);
    } else {
      LOG.debug("No user found with the email {}", email);
    }
  }

  /**
   * Deletes all tokens.
   */
  @Override
  @Transactional
  public void deleteAll() {
    passwordTokenRepository.deleteAll();
  }

  /**
   * Creates a new Password Reset Token for the user identified by the given email.
   *
   * @param email The email uniquely identifying the user
   * @return PasswordResetToken a new Password Reset Token for the user
   *         identified by the given email or null if none is found.
   */
  @Override
  @Transactional
  public Optional<PasswordToken> createTokenForEmail(final String email) {
    InputValidationUtility.validateInputs(getClass(), email);

    User existingUser = userRepository.findByEmail(email);
    if (Objects.nonNull(existingUser)) {
      String token = UUID.randomUUID().toString();
      PasswordToken passwordResetToken = new PasswordToken(token, existingUser);
      passwordResetToken.setId(existingUser.getId());
      // save the password token to the database
      passwordResetToken = passwordTokenRepository.save(passwordResetToken);
      userRepository.save(existingUser);
      LOG.debug("Successfully created token {} for user {}", token, existingUser);
      return Optional.of(passwordResetToken);

    } else {
      LOG.warn("User with email {} is not found in our records", email);
    }
    return Optional.empty();
  }
}
