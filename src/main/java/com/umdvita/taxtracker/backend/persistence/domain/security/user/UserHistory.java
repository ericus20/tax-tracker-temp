package com.umdvita.taxtracker.backend.persistence.domain.security.user;

import com.umdvita.taxtracker.backend.persistence.domain.base.BaseEntity;
import com.umdvita.taxtracker.enums.UserHistoryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * The UserHistory model for the application to keep track of all activities happening to the user account.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserHistory extends BaseEntity<Long> implements Serializable {
  private static final long serialVersionUID = 4561671472998625960L;

  @Enumerated(EnumType.ORDINAL)
  private UserHistoryType userHistoryType;

  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private User user;

  public UserHistory(UserHistoryType userHistoryType, User user) {
    this.userHistoryType = userHistoryType;
    this.user = user;
  }
}
