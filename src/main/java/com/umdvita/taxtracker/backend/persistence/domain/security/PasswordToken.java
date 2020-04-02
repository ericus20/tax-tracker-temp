package com.umdvita.taxtracker.backend.persistence.domain.security;

import com.umdvita.taxtracker.backend.persistence.domain.base.BaseEntity;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * This class creates a password token with option depending on users preferences.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"token"}, callSuper = false)
@Table(indexes = {@Index(name = "token_index", columnList = "token")})
public class PasswordToken extends BaseEntity implements Serializable {

  /**
   * Since the object will be travelling across several JVM this UID will
   * be to serialize and deserialize.
   */
  private static final long serialVersionUID = -3758747292048556944L;

  /**
   * 30 minutes as default.
   */
  public static final int DEFAULT_EXPIRY_TIME_IN_MINUTES = 30;


  /**
   * The token for password reset.
   */
  @Column(unique = true, nullable = false)
  private String token;

  /**
   * Expiry date for token.
   * LocalDateTime instance is not currently supported in SQL. It supports traditional Date object.
   * So we need to convert this new API (available after java 8) by ourselves because the
   * relational databases cannot do that yet.
   */
  private LocalDateTime expiryDate;

  /**
   * User owning this token.
   */
  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id", unique = true)
  private User user;

  /**
   * Constructor for creating token.
   *
   * @param passwordToken the token
   * @param user          the user
   */
  public PasswordToken(final String passwordToken, final User user) {
    this(passwordToken, user, DEFAULT_EXPIRY_TIME_IN_MINUTES);
  }

  /**
   * Constructor with option to set custom expiration date.
   *
   * @param passwordToken the token
   * @param user          the user
   * @param period        the custom expiration date
   */
  public PasswordToken(final String passwordToken, final User user, final int period) {
    int expiryDateInMinutes;
    if (period <= 0) {
      expiryDateInMinutes = DEFAULT_EXPIRY_TIME_IN_MINUTES;
    } else {
      expiryDateInMinutes = period;
    }

    this.token = passwordToken;
    this.user = user;
    this.expiryDate = LocalDateTime.now(Clock.systemUTC()).plusMinutes(expiryDateInMinutes);
  }
}
