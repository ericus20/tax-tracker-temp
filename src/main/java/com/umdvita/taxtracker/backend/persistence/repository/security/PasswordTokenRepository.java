package com.umdvita.taxtracker.backend.persistence.repository.security;

import com.umdvita.taxtracker.backend.persistence.domain.security.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The repository or DAO for password token domain model.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Repository
@Transactional(readOnly = true)
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {

  /**
   * Finds and return the Password Reset Token by token.
   *
   * @param token the token.
   * @return the password reset token.
   */
  PasswordToken findByToken(String token);

  /**
   * Finds and return the Password Reset Token reference by token.
   *
   * @param token the token.
   * @return the password reset token.
   */
  PasswordToken getOneByToken(String token);

  /**
   * Return all the password token instances.
   *
   * @return the list of password token
   */
  @NonNull
  List<PasswordToken> findAll();
}
