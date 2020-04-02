package com.umdvita.taxtracker.backend.persistence.domain.tax;

import com.umdvita.taxtracker.backend.persistence.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

import static javax.persistence.CascadeType.DETACH;

/**
 * The preparer is a user with elevated privileges to manage the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"taxReturn", "note"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"tax_return_id", "note"}))
public class TaxReturnNote extends BaseEntity implements Serializable {
  private static final long serialVersionUID = -6805075476613140657L;

  @Column(length = 500)
  private String note;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "tax_return_id")
  private TaxReturn taxReturn;

  public TaxReturnNote(String note, TaxReturn taxReturn) {
    this.note = note;
    this.taxReturn = taxReturn;
  }
}
