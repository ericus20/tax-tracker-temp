package com.umdvita.taxtracker.backend.persistence.domain.security.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umdvita.taxtracker.backend.persistence.domain.base.BaseEntity;
import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The user model for the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Entity
@NaturalIdCache
@ToString(exclude = {"roles", "userHistories", "password", "ssn"})
@EqualsAndHashCode(of = {"username", "email", "token"}, callSuper = false)
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email", "token"})},
        indexes = {@Index(name = "email_and_enabled_index", columnList = "email, enabled")})
public class User extends BaseEntity<Long> implements Serializable {

  /**
   * Since the object will be travelling across several JVM this UID will
   * be to serialize and deserialize.
   */
  private static final long serialVersionUID = -9091242468806129043L;

  /**
   * external id made visible to the public.
   */
  @NaturalId
  @Column(nullable = false, unique = true)
  private String token;

  @NaturalId
  @Column(unique = true, nullable = false)
  @Size(min = 3, message = "Username should be at least 3 characters")
  @NotBlank(message = "Username cannot be left blank")
  private String username;

  @JsonIgnore
  @NotBlank(message = "Password cannot be left blank")
  private String password;
  private String firstName;
  private String lastName;
  private String name;

  @JsonFormat(pattern = "mm/dd/yyyy")
  private String dob;

  /**
   * Email is part of primary keys to uniquely
   * identify a particular user.
   */
  @NaturalId
  @Email(message = "A valid email format is required")
  @NotBlank(message = "Email cannot be left blank")
  @Column(unique = true, nullable = false)
  private String email;

  /**
   * SSN is part of primary keys to uniquely
   * identify a particular user.
   */
  private String ssn;

  private String last4Ssn;
  private String phone;
  private String profileImageUrl;

  @Column(length = 500)
  private String preSignedProfileImageUrl;

  /**
   * If true then this user is enabled.
   */
  private boolean enabled;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<UserHistory> userHistories = new ArrayList<>();

  public void addUserHistory(UserHistory userHistory) {
    this.userHistories.add(userHistory);
    userHistory.setUser(this);
  }

  public void removeUserHistory(UserHistory userHistory) {
    this.userHistories.remove(userHistory);
    userHistory.setUser(null);
  }
}
