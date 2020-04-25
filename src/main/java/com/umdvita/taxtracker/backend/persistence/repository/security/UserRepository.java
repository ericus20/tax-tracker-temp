package com.umdvita.taxtracker.backend.persistence.repository.security;

import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The repository or DAO for user domain model.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Returns user that matches the specified username.
   *
   * @param username the username
   * @return the user
   */
  User findByUsername(String username);

  /**
   * Returns user reference that matches the specified username.
   *
   * @param username the username.
   * @return the user.
   */
  User getOneByUsername(String username);

  /**
   * Returns user that matches the specified email.
   *
   * @param email the email.
   * @return the user.
   */
  User findByEmail(String email);

  /**
   * Returns user reference that matches the specified email.
   *
   * @param email the email.
   * @return the user.
   */
  User getOneByEmail(String email);

  /**
   * Returns user that matches the specified token.
   *
   * @param token the token.
   * @return the user.
   */
  User findByToken(String token);

  /**
   * Returns user reference that matches the specified token.
   *
   * @param token the token.
   * @return the user.
   */
  User getOneByToken(String token);

  /**
   * Returns all users in the database.
   *
   * @return the users.
   */
  @NonNull
  List<User> findAll();

  /**
   * Returns all users in the database with a set preSignedUrl.
   *
   * @return the users.
   */
  @NonNull
  List<User> findByPreSignedProfileImageUrlNotNull();

  /**
   * Return list of randomized users with the specified count.
   *
   * @param rand the random number
   * @param size the size of list
   * @return list of questions
   */
  @Query(value = "SELECT * FROM users LIMIT ?1, ?2", nativeQuery = true)
  List<User> findRandomUserCount(Integer rand, Integer size);

  /**
   * Returns all users in the database not enabled.
   *
   * @return the users not enabled.
   */
  List<User> findByEnabledFalseOrderById();

  /**
   * Returns all enabled users in the database.
   *
   * @return the users not enabled.
   */
  List<User> findByEnabledTrueOrderById();

  /**
   * Checks if the username already exists.
   *
   * @param username the username
   * @return <code>true</code> if username exists
   */
  boolean existsByUsernameOrderById(String username);

  /**
   * Checks if the email already exists.
   *
   * @param email the email
   * @return <code>true</code> if email exists
   */
  boolean existsByEmailOrderById(String email);

  /**
   * Checks if the email already exists and the user is enabled.
   *
   * @param email the email
   * @return <code>true</code> if email exists and enabled
   */
  boolean existsByEmailAndEnabledTrueOrderById(String email);

  /**
   * Checks if the email already exists and the user is not enabled.
   *
   * @param email the email
   * @return <code>true</code> if email exists and not enabled
   */
  boolean existsByEmailAndEnabledFalseOrderById(String email);
}
