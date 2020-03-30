package com.umdvita.taxtracker.backend.persistence.domain.security;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The PersistentLogins class supports and allows user to use remember-me with persistent option.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"series"})
public class PersistentLogins implements Serializable {
  private static final long serialVersionUID = -7689448508274159102L;

  @Id
  @Column(length = 64)
  private String series;

  @Column(nullable = false, length = 64)
  private String username;

  @Column(nullable = false, length = 64)
  private String token;

  @Column(nullable = false)
  private Timestamp lastUsed;
}
