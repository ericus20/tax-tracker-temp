package com.umdvita.taxtracker.backend.persistence.domain.base;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class is to allow an entity to inherit common properties from it.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(of = {"version"})
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable, Identifiable<Long> {
  private static final long serialVersionUID = -1106817480796833080L;

  /**
   * Sequence Style Generator to auto generate ID based on the choices in
   * the parameters.
   */
  @GenericGenerator(
          name = "TaxTrackerSequenceGenerator",
          strategy = "com.umdvita.taxtracker.backend.persistence.domain.base.AssignedSequenceStyleGenerator",
          parameters = {
                  @Parameter(name = "sequence_name", value = "TaxTrackerSequence"),
                  @Parameter(name = "initial_value", value = "1"),
                  @Parameter(name = "increment_size", value = "1")
          }
  )

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TaxTrackerSequenceGenerator")
  private Long id;

  /**
   * Manages the version of Entities to measure the amount of
   * modifications made to this entity.
   */
  @Version
  private short version;

  /**
   * Keeps record of when an Entity wss created.
   */
  @Expose
  @CreatedDate
  @CreationTimestamp
  private LocalDateTime createdAt;

  /**
   * Records who updated an Entity by saving username.
   */
  @CreatedBy
  private String createdBy;

  /**
   * Keeps record of when the item was last Modified.
   */
  @UpdateTimestamp
  @LastModifiedDate
  private LocalDateTime updatedAt;

  /**
   * Manages the timestamps for each update made to an Entity.
   */
  @LastModifiedBy
  private String updatedBy;

}
