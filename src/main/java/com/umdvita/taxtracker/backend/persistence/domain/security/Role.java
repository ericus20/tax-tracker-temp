package com.umdvita.taxtracker.backend.persistence.domain.security;

import com.umdvita.taxtracker.enums.RoleType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The role model for the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Entity
@Cacheable
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class Role implements Serializable {

  /**
   * Since the object will be travelling across several JVM this UID will
   * be to serialize and deserialize.
   */
  private static final long serialVersionUID = -4726597492234568939L;

  @Id
  private Short id;

  /**
   * The name for this role.
   */
  @Column(nullable = false, unique = true)
  private String name;

  /**
   * Constructor for Role injection.
   *
   * @param roleType the role enum.
   */
  public Role(final RoleType roleType) {
    this.setId(roleType.getId());
    this.name = roleType.getName();
  }

}
