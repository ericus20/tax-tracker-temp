package com.umdvita.taxtracker.backend.persistence.domain.tax;

import com.umdvita.taxtracker.backend.persistence.domain.base.BaseEntity;
import com.umdvita.taxtracker.backend.pojo.SeparateDateFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

import static javax.persistence.CascadeType.DETACH;

/**
 * The TaxHistory model for the application.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@Entity
@ToString(exclude = "taxReturn")
@EqualsAndHashCode(callSuper = false)
@Table(indexes = {@Index(name = "tax_return_index", columnList = "tax_return_id")})
public class TaxHistory extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 2097095683169960990L;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "tax_return_id")
  private TaxReturn taxReturn;

  @Transient
  private transient SeparateDateFormat separateDateFormat;

  @Transient
  private transient String timeElapsedDescription;
}
