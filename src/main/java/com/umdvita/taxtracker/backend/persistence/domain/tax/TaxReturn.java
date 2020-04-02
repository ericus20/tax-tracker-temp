package com.umdvita.taxtracker.backend.persistence.domain.tax;

import com.umdvita.taxtracker.backend.persistence.domain.base.BaseEntity;
import com.umdvita.taxtracker.backend.persistence.domain.security.user.User;
import com.umdvita.taxtracker.enums.TaxReturnStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The TaxReturn model for the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false, of = {"user", "year", "taxReturnStatus"})
@ToString(exclude = {"user", "taxReturnNotes"})
public class TaxReturn extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1273809599754062913L;

  @Enumerated(value = EnumType.ORDINAL)
  @Column(name = "tax_return_status")
  private TaxReturnStatus taxReturnStatus;

  private String year;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id", unique = true)
  private User user;

  private LocalDateTime takeOverTime;

  @OneToMany(mappedBy = "taxReturn", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<TaxReturnNote> taxReturnNotes = new ArrayList<>();

  @OneToMany(mappedBy = "taxReturn", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<TaxHistory> taxHistories = new ArrayList<>();

  public void addTaxReturnNote(TaxReturnNote taxReturnNote) {
    this.taxReturnNotes.add(taxReturnNote);
    taxReturnNote.setTaxReturn(this);
  }

  public void removeTaxReturnNote(TaxReturnNote taxReturnNote) {
    this.taxReturnNotes.remove(taxReturnNote);
    taxReturnNote.setTaxReturn(null);
  }

  public void addTaxHistory(TaxHistory taxHistory) {
    this.taxHistories.add(taxHistory);
    taxHistory.setTaxReturn(this);
  }

  public void removeTaxHistory(TaxHistory taxHistory) {
    this.taxHistories.remove(taxHistory);
    taxHistory.setTaxReturn(null);
  }
}
