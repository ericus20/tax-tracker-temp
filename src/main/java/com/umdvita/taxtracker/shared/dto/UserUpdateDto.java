package com.umdvita.taxtracker.shared.dto;

import com.umdvita.taxtracker.backend.persistence.domain.security.Role;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.UserHistory;
import com.umdvita.taxtracker.shared.dto.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The UserDto transfers user details from outside into the application and vice versa.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@ToString(exclude = {"roles"})
@EqualsAndHashCode(callSuper = true)
public class UserUpdateDto extends UserDto implements Serializable {
  private static final long serialVersionUID = -2423636754258148802L;

  private Set<Role> roles = new HashSet<>();
  private List<UserHistory> userHistories = new ArrayList<>();

  public UserUpdateDto(String email) {
    this.setEmail(email);
  }
}
